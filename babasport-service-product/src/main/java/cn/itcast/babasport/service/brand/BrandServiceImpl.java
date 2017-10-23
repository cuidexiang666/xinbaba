package cn.itcast.babasport.service.brand;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.babasport.mapper.brand.BrandMapper;
import cn.itcast.babasport.pojo.brand.Brand;
import cn.itcast.babasport.pojo.brand.BrandQuery;
import cn.itcast.babasport.pojo.page.Pagination;
import redis.clients.jedis.Jedis;


@Service("brandService")
public class BrandServiceImpl  implements BrandService{

	@Resource
	private BrandMapper brandMapper;
	
	@Resource
	private Jedis jedis;
	@Override
	public List<Brand> selectBrandQuery(String name, Integer isDisplay) {
		
		BrandQuery brandQuery = new BrandQuery();
		//判断传入的参数是否为空
		if (StringUtils.isNotBlank(name)) {
			brandQuery.setName(name);
		}
		if (isDisplay!=null) {
			brandQuery.setIsDisplay(isDisplay);
		}
		return brandMapper.selectBrandQuery(brandQuery);
	}
	@Override
	public Pagination selectBrandHavePage(String name, Integer isDisplay, Integer pageNo) {
		
		
		BrandQuery brandQuery = new BrandQuery();
		StringBuilder params = new StringBuilder();
		if (StringUtils.isNotBlank(name)) {
			brandQuery.setName(name);
			params.append("name=").append(name);
		}
		if (isDisplay!=null) {
			brandQuery.setIsDisplay(isDisplay);
			params.append("&isDisplay=").append(isDisplay);
		}
		//设置每页显示的个数
		brandQuery.setPageSize(5);
		//设置当前页
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		Integer startRow = (brandQuery.getPageNo()-1)*brandQuery.getPageSize();
		brandQuery.setStartRow(startRow);
		//查询结果集
		List<Brand> brands = brandMapper.selectBrandHavePage(brandQuery);
		//查询总条数
		int count = brandMapper.queryCount(brandQuery);
		//封装数据
		Pagination pagination = new Pagination(brandQuery.getPageNo(), brandQuery.getPageSize(), count, brands);
		
		//添加分页工具栏
		String url = "/brand/list.do";
		pagination.pageView(url, params.toString());
		
		return pagination;
	}
	@Override
	public Brand selectBrandById(Long id) {
		return brandMapper.selectBrandById(id);
	}
	/**
	 * 品牌修改
	 */
	@Transactional
	@Override
	public void updateBrandById(Brand brand) {
		//将品牌信息保存到redis中
		jedis.hset("brand", String.valueOf(brand.getId()), brand.getName());
		brandMapper.updateBrandById(brand);
	}
	@Override
	public void saveBrand(Brand brand) {
		brandMapper.saveBrand(brand);
	}
	@Override
	public void deleteBratchBrand(Long[] ids) {
		brandMapper.deleteBratchBrand(ids);
	}

	
}
