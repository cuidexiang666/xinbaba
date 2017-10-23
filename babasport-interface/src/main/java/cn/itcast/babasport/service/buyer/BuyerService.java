package cn.itcast.babasport.service.buyer;

import cn.itcast.babasport.pojo.user.Buyer;

public interface BuyerService {

	/**
	 * 根据用户名获取账号信息
	 * @param username
	 * @return
	 */
	public Buyer selectBuyerByUsername(String username);
}
