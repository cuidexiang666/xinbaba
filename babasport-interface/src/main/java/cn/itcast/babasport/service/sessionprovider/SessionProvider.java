package cn.itcast.babasport.service.sessionprovider;

public interface SessionProvider {

	/**
	 * 将用户信息保存到redis中
	 * @param key
	 * @param username
	 */
	public void setAttributeForUserName(String key,String username);
	
	/**
	 * 从redis中获取用户信息
	 * @return
	 */
	public String getAttributeForUserName(String key);
}
