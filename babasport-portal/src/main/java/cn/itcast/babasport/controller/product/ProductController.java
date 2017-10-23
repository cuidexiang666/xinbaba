package cn.itcast.babasport.controller.product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.babasport.pojo.brand.Brand;
import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.service.ad.AdService;
import cn.itcast.babasport.service.cms.CmsService;
import cn.itcast.babasport.service.search.SearchService;

@Controller
public class ProductController {

	@Resource
	private SearchService searchService;
	
	@Resource
	private CmsService cmsService;
	
	@Resource
	private AdService adService;
	
	@RequestMapping("/")
	public String index(Model model){
		String ads = adService.selectAdsBypositionId(89L);
		model.addAttribute("ads", ads);
		return "index";
	}
	
	/**
	 * 去商品查询页面
	 * @param keyword
	 * @param pageNo
	 * @param price
	 * @param brandId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/Search")
	public String search(String keyword,Integer pageNo,String price,Long brandId,Model model) throws Exception{
		
		//筛选条件回显
		Map<String, String> map = new HashMap<>();
		if (brandId!=null) {
			String name = searchService.selectBrandNameByIdFromRedis(brandId);
			map.put("品牌:", name);
		}
		if (StringUtils.isNotBlank(price)) {
			String[] prices = price.split("-");
			if (prices.length==2) {
				map.put("价格:", price);
			}else {
				map.put("价格:", price+"以上");
			}
		}
		model.addAttribute("map", map);
		//从redis中查询品牌信息
		List<Brand> brands = searchService.selectBrandFromRedis();
		model.addAttribute("brands", brands);
		Pagination pagination = searchService.searchProductHavePageForPortal(keyword, price, brandId, pageNo);
		model.addAttribute("pagination", pagination);
		//给页面返回查询的条件
		model.addAttribute("keyword", keyword);
		model.addAttribute("price", price);
		model.addAttribute("brandId", brandId);
		return "search";
	}
	
	
	/**
	 * 去商品详情页
	 * @param Id
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/detail")
	public String detail(Long id,Model model){
		//查询出对应的商品
		Product product = cmsService.selectProductById(id);
		model.addAttribute("product", product);
		//查询相应商品对应的库存
		List<Sku> skus = cmsService.selectSkusByProIdWithStorkMoreZero(id);
		model.addAttribute("skus", skus);
		//处理颜色重复问题，将其放入set集合
		Set<Color> colors = new HashSet<>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		model.addAttribute("colors", colors);
		return "product";
	}
	
}
