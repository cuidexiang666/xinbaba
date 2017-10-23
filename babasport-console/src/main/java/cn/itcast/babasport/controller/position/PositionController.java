package cn.itcast.babasport.controller.position;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.babasport.pojo.ad.Advertising;
import cn.itcast.babasport.pojo.ad.Position;
import cn.itcast.babasport.service.ad.AdService;

@Controller
@RequestMapping("position")
public class PositionController {

	@Resource
	private AdService adService;
	
	@RequestMapping("/tree.do")
	public String tree(String root,Model model){
		List<Position> list = new ArrayList<>();
		//点击广告tab页是，root的默认值为source
		if ("source".equals(root)) {//最顶点时，查询ID为0的数据
			list = adService.selectPositionListByParentId(0L);
		}else {//否则传递对应的ID
			list = adService.selectPositionListByParentId(Long.parseLong(root));
		}
		model.addAttribute("list", list);
		return "position/tree";
	}
	
}
