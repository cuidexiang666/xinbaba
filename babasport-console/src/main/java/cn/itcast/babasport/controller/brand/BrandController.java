package cn.itcast.babasport.controller.brand;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.babasport.pojo.brand.Brand;
import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.service.brand.BrandService;

@Controller
@RequestMapping("brand")
public class BrandController {

	@Resource 
	private BrandService brandService;
	
	
	/**
	 * 不分页查询
	 * @param name
	 * @param isDisplay
	 * @param model
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(String name,Integer isDisplay,Model model,Integer pageNo){
		
		//默认查询可用的品牌
		if (isDisplay == null) {
			isDisplay = 1;
		}
		//不分页查询
//		List<Brand> brands = brandService.selectBrandQuery(name, isDisplay);
//		model.addAttribute("brands", brands);
		
		//分页查询
		Pagination pagination = brandService.selectBrandHavePage(name, isDisplay, pageNo);
		model.addAttribute("pagination", pagination);
		
		//查询条件回显
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", isDisplay);
		model.addAttribute("pageNo", pageNo);
		
		return "brand/list";
	}
	
	
	@RequestMapping("edit.do")
	public String edit(Long id,Model model){
		
		Brand brand = brandService.selectBrandById(id);
		model.addAttribute("brand", brand);
		return "/brand/edit";
	}
	
	@RequestMapping("update.do")
	public String update(Brand brand){
		brandService.updateBrandById(brand);
		return "redirect:list.do";
	}
	
	@RequestMapping("add")
	public String add(){
		return"brand/add";
	}
	
	
	@RequestMapping("save")
	public String save(Brand brand){
		brandService.saveBrand(brand);
		return "redirect:list.do";
	}
	
	@RequestMapping("deleteBratchBrand.do")
	public String deleteBratchBrand(Long[] ids){
		
		brandService.deleteBratchBrand(ids);
		return "forward:list.do";
	}
	
}
