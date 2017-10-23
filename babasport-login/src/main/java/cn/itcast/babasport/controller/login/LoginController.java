package cn.itcast.babasport.controller.login;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.babasport.pojo.user.Buyer;
import cn.itcast.babasport.service.buyer.BuyerService;
import cn.itcast.babasport.service.sessionprovider.SessionProvider;
import cn.itcast.babasport.utils.ticket.TicketUtils;

@Controller
public class LoginController {

	@Resource
	private BuyerService buyerService;
	
	@Resource
	private SessionProvider sessionProvider;
	
	/**
	 * 跳转到登录页面
	 * @return
	 */
	@RequestMapping(value="/login.aspx",method={RequestMethod.GET})
	public String login(){
		return "login";
	}
	
	/**
	 * 登录操作
	 * @param username
	 * @param password
	 * @param ReturnUrl
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login.aspx",method={RequestMethod.POST})
	public String login(String username,String password,String ReturnUrl,
			HttpServletRequest request,HttpServletResponse response,Model model){
		
		//用户名不能为空；
		if (StringUtils.isNotBlank(username)) {
			//用户名必须正确；
			Buyer buyer = buyerService.selectBuyerByUsername(username);
			if (buyer != null) {
				//密码不能为空；
				if (StringUtils.isNotBlank(password)) {
					//密码必须正确；
					if (buyer.getPassword().equals(encodePassword(password))) {
						//说明校验通过，保存到cookie和redis
						sessionProvider.setAttributeForUserName(TicketUtils.getCSESSIONID(request, response), username);
						//跳转到登录前页面
						return "redirect:"+ReturnUrl;
					}
				}else {
					model.addAttribute("error", "密码不能为空!");
				}
			}else {
				model.addAttribute("error", "用户名错误!");
			}
		}else {
			model.addAttribute("error", "用户名不能为空!");
		}
		return "login";
	}
	
	@RequestMapping("/isLogin.aspx")
	@ResponseBody
	public MappingJacksonValue isLogin(String callback,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String userName = sessionProvider.getAttributeForUserName(TicketUtils.getCSESSIONID(request, response));
		String flag = "0";
		if (StringUtils.isNotBlank(userName)) {//已经登录
			flag = "1";
		}
		
		//存在跨域问题
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("flag", flag);
//		response.setContentType("application/json;charset=utf-8");
//		response.getWriter().write(jsonObject.toString());
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(flag);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
		
	}
	
	
	
	/**
	 * 密码加密。规则： MD5 + 十六进制 
	 * @param password
	 * @return
	 */
	public String encodePassword(String password){
		
		String algorithm = "MD5";
		char[] encodeHex  = null;
		try {
			MessageDigest instance = MessageDigest.getInstance(algorithm);
			// MD5加密后密文
			byte[] digest = instance.digest(password.getBytes());
			// 十六进制再加密一次
			encodeHex = Hex.encodeHex(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(encodeHex);
	}
}



