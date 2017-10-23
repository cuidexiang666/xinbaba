package cn.itcast.babasport.service.buyer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.user.BuyerMapper;
import cn.itcast.babasport.pojo.user.Buyer;
import cn.itcast.babasport.pojo.user.BuyerQuery;
import cn.itcast.babasport.service.buyer.BuyerService;

@Service("buyerService")
public class BuyerServiceImpl implements BuyerService{

	@Resource
	private BuyerMapper buyerMapper;
	
	/**
	 * 根据用户名获取账号信息
	 */
	@Override
	public Buyer selectBuyerByUsername(String username) {
		BuyerQuery buyerQuery = new BuyerQuery();
		buyerQuery.createCriteria().andUsernameEqualTo(username);
		List<Buyer> list = buyerMapper.selectByExample(buyerQuery);
		if (list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
