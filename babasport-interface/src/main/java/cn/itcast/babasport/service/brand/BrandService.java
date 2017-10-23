package cn.itcast.babasport.service.brand;

import java.util.List;

import cn.itcast.babasport.pojo.brand.Brand;
import cn.itcast.babasport.pojo.page.Pagination;


public interface BrandService {

	/**
	 * 条件查询不分页
	 * @param name
	 * @param isDisplay
	 * @return
	 */
	public List<Brand> selectBrandQuery(String name,Integer isDisplay);
	
	
	/**
	 * 品牌列表查询分页
	 * @param name
	 * @param isDisplay
	 * @param pageNo
	 * @return
	 */
	public Pagination selectBrandHavePage(String name,Integer isDisplay,Integer pageNo);
	
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public Brand selectBrandById(Long id);
	
	/**
	 * 更新品牌
	 * @param brand
	 */
	public void updateBrandById(Brand brand);
	
	/**
	 * 添加品牌
	 * @param brand
	 */
	public void saveBrand(Brand brand);

	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void deleteBratchBrand(Long[] ids);
}




