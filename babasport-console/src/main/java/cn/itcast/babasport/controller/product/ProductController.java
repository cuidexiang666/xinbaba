package cn.itcast.babasport.controller.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.itcast.babasport.pojo.brand.Brand;
import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.service.brand.BrandService;
import cn.itcast.babasport.service.color.ColorService;
import cn.itcast.babasport.service.product.ProductService;
import cn.itcast.babasport.utils.contants.BbsContants;
import cn.itcast.babasport.utils.upload.FastDFSUtils;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Resource
	private ProductService productService;
	@Resource
	private BrandService brandService;
	@Resource
	private ColorService colorService;
	
	@RequestMapping("/list.do")
	public String list(Integer pageNo,String name,Long brandId,Boolean isShow,Model model){
		
		List<Brand> brands = brandService.selectBrandQuery(null, 1);
		model.addAttribute("brands", brands);
		
		Pagination pagination = productService.selectProductHavePage(pageNo, name, brandId, isShow);
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("isShow", isShow);
		model.addAttribute("brandId", brandId);
		model.addAttribute("pageNo", pageNo);
		return "product/list";
	}
	
	@RequestMapping("/add.do")
	public String add(Model model){
		//初始化颜色信息
		List<Color> colors = colorService.selectColorList();
		model.addAttribute("colors", colors);
		
		//初始化品牌信息
		List<Brand> brands = brandService.selectBrandQuery(null, 1);
		model.addAttribute("brands", brands);
		
		return "product/add";
	}
	
	
	@RequestMapping("save.do")
	public String save(Product product){
		
		productService.insertProduct(product);
		return "redirect:list.do";
	}
	
	@RequestMapping("/isShow.do")
	public String isShow(Long[] ids) throws Exception{
		
		productService.isShow(ids);
		return "redirect:list.do";
	}
	
}
