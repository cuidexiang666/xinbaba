package cn.itcast.babasport.controller.frame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frame")
public class FrameController {

	@RequestMapping("/product_main.do")
	public String product_main(){
		
		return "frame/product_main";
	}
	
	@RequestMapping("/product_left.do")
	public String product_left(){
		
		return "frame/product_left";
	}
	
	/**
	 * 跳转到商品页
	 * @return
	 */
	@RequestMapping("/list")
	public String list(){
		
		return "product/list";
	}
	/**
	 * 跳转到广告页
	 * @return
	 */
	@RequestMapping("/ad_main.do")
	public String ad_main(){
		return "frame/ad_main";
	}
	
	/**
	 * 跳转到ad_left页面
	 * @return
	 */
	@RequestMapping("/ad_left.do")
	public String ad_left(){
		return "frame/ad_left";
	}
}
