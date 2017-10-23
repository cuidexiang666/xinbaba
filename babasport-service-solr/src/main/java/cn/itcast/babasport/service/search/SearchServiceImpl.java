package cn.itcast.babasport.service.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import cn.itcast.babasport.mapper.product.ProductMapper;
import cn.itcast.babasport.mapper.product.SkuMapper;
import cn.itcast.babasport.pojo.brand.Brand;
import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.ProductQuery;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.pojo.product.SkuQuery;
import redis.clients.jedis.Jedis;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

	@Resource
	private SolrServer solrServer;
	
	@Resource
	private Jedis jedis;
	
	@Resource
	private ProductMapper productMapper;
	
	@Resource
	private SkuMapper skuMapper;
	
	/**
	 * 前台系统根据关键字检索
	 * @throws Exception 
	 */
	@Override
	public Pagination searchProductHavePageForPortal(String keyword,String price,Long brandId,Integer pageNo) throws Exception {
		// 一、创建SolrQuery对象封装查询条件
				SolrQuery solrQuery = new SolrQuery();
				StringBuilder params = new StringBuilder(); // 封装分页工具栏需要的参数
				ProductQuery productQuery = new ProductQuery();
				productQuery.setPageNo(Pagination.cpn(pageNo));
				productQuery.setPageSize(8);
				// 1、根据关键字检索
				if(keyword != null && !"".equals(keyword)){
					solrQuery.setQuery("name_ik:"+keyword);
					params.append("keyword=").append(keyword);
				}
				// 2、关键字高亮
				solrQuery.setHighlight(true); // 开启高亮
				solrQuery.addHighlightField("name_ik"); // 对哪个字段进行高亮
				solrQuery.setHighlightSimplePre("<font color='red'>"); // 标签前缀
				solrQuery.setHighlightSimplePost("</font>"); // 标签结束
				// 3、根据价格排序
				solrQuery.setSort("price", ORDER.asc);
				// 4、分页
				solrQuery.setStart(productQuery.getStartRow()); // 其实行
				solrQuery.setRows(productQuery.getPageSize()); // 每页显示的条数
				//5.实现条件过滤  （通过价格和品牌）
				if (brandId!=null) {
					solrQuery.addFilterQuery("brandId:"+brandId);
					params.append("&brandId=").append(brandId);
				}
				if (StringUtils.isNotBlank(price)) {
					String[] prices = price.split("-");
					if (prices.length==2) {
						solrQuery.addFilterQuery("price:["+prices[0]+" TO "+prices[1]+"]");
					}else {
						solrQuery.addFilterQuery("price:["+price+" TO *]");
					}
					params.append("&price=").append(price);
				}
				// 二、根据SolrQuery查询
				QueryResponse queryResponse = solrServer.query(solrQuery);
				SolrDocumentList results = queryResponse.getResults(); // 获取普通结果集
				int totalCount = (int) results.getNumFound(); // 总条数
				// key1:id key2:name_ik list:高亮结果集
				Map<String, Map<String, List<String>>> hl = queryResponse.getHighlighting(); // 高亮结果集
				
				// 三、处理结果集
				List<Product> list = new ArrayList<>();
				for (SolrDocument solrDocument : results) {
					Product product = new Product();
					String id = solrDocument.get("id").toString();
					String imgUrl = solrDocument.get("url").toString();
					List<String> names = hl.get(id).get("name_ik");
					if(names != null && names.size() > 0){ // 有高亮
						product.setName(names.get(0));
					}else{
						product.setName(solrDocument.get("name_ik").toString());
					}
					product.setId(Long.parseLong(id));
					product.setImgUrl(imgUrl);
					product.setBrandId(Long.parseLong(solrDocument.get("brandId").toString()));
					product.setPrice(solrDocument.get("price").toString());
					list.add(product);
				}
				
				// 四、创建分页对象并且封装数据
				Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), totalCount, list);
				String url = "/Search";
				pagination.pageView(url, params.toString());
				
				return pagination;
			}

	/**
	 * 从redis中获取品牌信息
	 */
	@Override
	public List<Brand> selectBrandFromRedis() {
		//获取到brand信息
		Map<String, String> map = jedis.hgetAll("brand");
		Set<Entry<String,String>> entrySet = map.entrySet();
		List<Brand> brands = new ArrayList<>();
		for (Entry<String, String> entry : entrySet) {
			Brand brand = new Brand();
			brand.setId(Long.valueOf(entry.getKey()));
			brand.setName(entry.getValue());
			brands.add(brand);
		}
		return brands;
	}

	/**
	 * 根据品牌ID在redis中查询品牌名称
	 */
	@Override
	public String selectBrandNameByIdFromRedis(Long brandId) {
		String name = jedis.hget("brand", String.valueOf(brandId));
		return name;
	}

	/**
	 * 将商品ID添加到solr中
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Override
	public void insertProductToSolr(Long id) throws Exception {
		// 将商品信息保存到索引库中
		Product goods = productMapper.selectByPrimaryKey(id);
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", id); // 商品ID
		doc.addField("name_ik", goods.getName()); // 商品名称
		doc.addField("url", goods.getImgUrl()); // 商品图片
		doc.addField("brandId", goods.getBrandId()); // 商品所属品牌
		// 给用户展示最低的价格
		// 从sku中查询：select price from bbs_sku where product_id = ? order by price asc limit 0,1
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.setFields("price");
		skuQuery.createCriteria().andProductIdEqualTo(id);
		skuQuery.setOrderByClause("price asc");
		skuQuery.setPageNo(1);
		skuQuery.setPageSize(1);
		List<Sku> skus = skuMapper.selectByExample(skuQuery);
		doc.addField("price", skus.get(0).getPrice()); // 商品最低价
		solrServer.add(doc);
		solrServer.commit();
	}
}
