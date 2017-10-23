package cn.itcast.babasport.service.product;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.babasport.mapper.product.ProductMapper;
import cn.itcast.babasport.mapper.product.SkuMapper;
import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.ProductQuery;
import cn.itcast.babasport.pojo.product.ProductQuery.Criteria;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.pojo.product.SkuQuery;
import redis.clients.jedis.Jedis;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Resource
	private ProductMapper productMapper;
	@Resource
	private SkuMapper skuMapper;
	@Resource
	private Jedis jedis;
	@Resource
	private SolrServer solrServer;
	@Resource
	private JmsTemplate jmsTemplate;
	
	@Override
	public Pagination selectProductHavePage(Integer pageNo, String name, Long brandId, Boolean isShow) {
		
		//创建productQuery对象封装条件
		ProductQuery productQuery = new ProductQuery();
		Criteria criteria = productQuery.createCriteria();
		
		//封装参数
		StringBuffer params = new StringBuffer();
		
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%"+name+"%");
			params.append("name=").append(name);
		}
		if (brandId != null) {
			criteria.andBrandIdEqualTo(brandId);
			params.append("&brandId=").append(brandId);
		}
		if (isShow != null) {
			criteria.andIsShowEqualTo(isShow);
			params.append("&isShow=").append(isShow);
		}else {
			criteria.andIsShowEqualTo(false);
			params.append("&isShow=").append(false);
		}
		
		productQuery.setPageNo(Pagination.cpn(pageNo));//当前页码
		productQuery.setPageSize(5);//每页显示的条数
		productQuery.setOrderByClause("id desc");
		
		//查询
		List<Product> products = productMapper.selectByExample(productQuery);
		//查询总记录数
		int totalCount = productMapper.countByExample(productQuery);
		
		//创建分页对象并封装数据
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), totalCount, products);
		
		//构建分页工具栏
		String url = "/product/list.do";
		pagination.pageView(url, params.toString());
		
		return pagination;
	}

	@Override
	public void insertProduct(Product product) {
		//添加商品
		//通过redis生成商品ID
		Long id = jedis.incr("pno");
		product.setId(id);
		product.setIsShow(false);//录入商品默认下架
		product.setCreateTime(new Date()); // 商品录入时间
		productMapper.insertSelective(product);
		
		//初始化商品的库存信息
		String[] colors = product.getColors().split(",");
		String[] sizes = product.getSizes().split(",");
		for (String size : sizes) {
			for (String color : colors) {
				Sku sku = new Sku();
				sku.setColorId(Long.parseLong(color));
				sku.setDeliveFee(0f);
				sku.setMarketPrice(0f);
				sku.setPrice(0f);
				sku.setProductId(id);
				sku.setSize(size);
				sku.setStock(0);
				sku.setUpperLimit(0);
				skuMapper.insertSelective(sku);
			}
		}
		
	}

	@Transactional
	@Override
	public void isShow(Long[] ids) throws Exception {
		// 1、修改isShow的状态
		Product product = new Product();
		product.setIsShow(true);
		for (final Long id : ids) {
			product.setId(id);
			productMapper.updateByPrimaryKeySelective(product);
			
			//将商品ID发送到activeMQ容器
			jmsTemplate.send(new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage(String.valueOf(id));
					return textMessage;
				}
			});
			
			// 2、将商品信息保存到索引库中
			/*Product goods = productMapper.selectByPrimaryKey(id);
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
			solrServer.commit();*/
		}
		
	}
}
