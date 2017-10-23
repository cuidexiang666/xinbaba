package cn.itcast.babasport.service.product;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Product;

public interface ProductService {

	/**
	 * 商品列表分页
	 * @param pageNo
	 * @param name
	 * @param brandId
	 * @param isShow
	 * @return
	 */
	public Pagination selectProductHavePage(Integer pageNo,String name,Long brandId,Boolean isShow);

	/**
	 * 添加商品
	 * @param product
	 */
	public void insertProduct(Product product);
	
	/**
	 * 商品上架
	 * @param ids
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @throws Exception 
	 */
	public void isShow(Long[] ids) throws Exception;
}
