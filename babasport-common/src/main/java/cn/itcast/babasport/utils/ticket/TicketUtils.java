package cn.itcast.babasport.utils.ticket;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.babasport.utils.contants.BbsContants;

public class TicketUtils {

	public static String getCSESSIONID(HttpServletRequest request,HttpServletResponse response){
		//判断cookie中是否有数据
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length>0) {
			for (Cookie cookie : cookies) {
				if (BbsContants.CSESSIONID.equals(cookie)) {
					//说明存在
					return cookie.getValue();
				}
			}
		}
		//如果没有，说明是第一次访问，我们需要创建该票据并保存到cookie中
		String CSESSIONID = UUID.randomUUID().toString().replace("-", "");
		Cookie cookie = new Cookie(BbsContants.CSESSIONID, CSESSIONID);
		cookie.setMaxAge(60*60);
		cookie.setPath("/");
		response.addCookie(cookie);
		return CSESSIONID;
	}
}
