package cn.itcast.babasport.service.search;

import java.util.List;

import cn.itcast.babasport.pojo.brand.Brand;
import cn.itcast.babasport.pojo.page.Pagination;

public interface SearchService {

	/**
	 * 前台系统根据关键字检索
	 * @return
	 * @throws Exception 
	 */
	public Pagination searchProductHavePageForPortal(String keyword,String price,Long brandId,Integer pageNo) throws Exception;
	
	/**
	 * 从redis中获取品牌信息
	 * @return
	 */
	public List<Brand> selectBrandFromRedis();
	
	/**
	 * 根据品牌ID在redis中查询品牌名称
	 * @param brandId
	 * @return
	 */
	public String selectBrandNameByIdFromRedis(Long brandId);
	
	/**
	 * 将商品ID添加到solr中
	 * @param id
	 * @throws Exception 
	 */
	public void insertProductToSolr(Long id) throws Exception;
	
}
