package cn.itcast.babasport.mapper.brand;

import java.util.List;

import cn.itcast.babasport.pojo.brand.Brand;
import cn.itcast.babasport.pojo.brand.BrandQuery;

public interface BrandMapper {

	/**
	 * 品牌列表查询不分页
	 * @param brandQuery
	 * @return
	 */
	public List<Brand> selectBrandQuery(BrandQuery brandQuery);
	
	/**
	 * 分页查询品牌列表
	 * @param brandQuery
	 * @return
	 */
	public List<Brand> selectBrandHavePage(BrandQuery brandQuery);
	
	/**
	 * 查询总记录数
	 * @param brandQuery
	 * @return
	 */
	public int queryCount(BrandQuery brandQuery);
	
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


