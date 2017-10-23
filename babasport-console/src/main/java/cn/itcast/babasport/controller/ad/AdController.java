package cn.itcast.babasport.controller.ad;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.babasport.pojo.ad.Advertising;
import cn.itcast.babasport.service.ad.AdService;

@Controller
@RequestMapping("/ad")
public class AdController {

	@Resource
	private AdService adService;
	
	@RequestMapping("/list.do")
	public String list(String root,Model model){
		List<Advertising> ads = adService.selectAdsByPOsitionId(Long.parseLong(root));
		model.addAttribute("ads", ads);
		model.addAttribute("positionId", root);
		return "ad/list";
	}
	
	/**
	 * 去广告添加页
	 * @param positionId
	 * @param model
	 * @return
	 */
	@RequestMapping("/add.do")
	public String add(Long positionId,Model model){
		model.addAttribute("positionId", positionId);
		return "ad/add";
	}
	
	@RequestMapping("/save.do")
	public String save(Advertising advertising){
		adService.insertAd(advertising);
		return "redirect:list.do?root="+advertising.getPositionId();
	}
}
