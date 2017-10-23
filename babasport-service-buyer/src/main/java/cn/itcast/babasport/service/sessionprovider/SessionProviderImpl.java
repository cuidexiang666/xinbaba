package cn.itcast.babasport.service.sessionprovider;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils.Null;

import cn.itcast.babasport.service.sessionprovider.SessionProvider;
import redis.clients.jedis.Jedis;

public class SessionProviderImpl implements SessionProvider {

	@Resource 
	private Jedis jedis;
	
	private  Integer expire = 60;
	public void setExpire(Integer expire) {
		this.expire = expire;
	}

	/**
	 * 将用户信息保存到redis中
	 */
	@Override
	public void setAttributeForUserName(String key, String username) {

		//将用户信息保存到redis中
		jedis.set("USER_SESSION:"+key, username);
		//设置用户过期时间
		jedis.expire(username, 60*expire);
	}

	/**
	 * 从redis中获取用户信息
	 */
	@Override
	public String getAttributeForUserName(String key) {
		String username = jedis.get("USER_SESSION:"+key);
		if (username!=null) {
			//重置过期时间
			jedis.expireAt(username, 60*expire);
			return username;
		}
		return null;
	}

}
