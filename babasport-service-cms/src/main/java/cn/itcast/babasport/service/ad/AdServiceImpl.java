package cn.itcast.babasport.service.ad;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.ad.AdvertisingMapper;
import cn.itcast.babasport.mapper.ad.PositionMapper;
import cn.itcast.babasport.pojo.ad.Advertising;
import cn.itcast.babasport.pojo.ad.AdvertisingQuery;
import cn.itcast.babasport.pojo.ad.Position;
import cn.itcast.babasport.pojo.ad.PositionQuery;
import cn.itcast.babasport.utils.json.JsonUtils;
import redis.clients.jedis.Jedis;

@Service("adService")
public class AdServiceImpl implements AdService {

	@Resource
	private PositionMapper positionMapper;
	
	@Resource
	private AdvertisingMapper advertisingMapper;
	
	@Resource
	private Jedis jedis;
	
	/**
	 * 加载广告位
	 */
	@Override
	public List<Position> selectPositionListByParentId(Long parentId) {
		PositionQuery positionQuery = new PositionQuery();
		positionQuery.createCriteria().andParentIdEqualTo(parentId);
		List<Position> list = positionMapper.selectByExample(positionQuery);
		return list;
	}

	/**
	 * 查询指定广告位下的广告列表
	 */
	@Override
	public List<Advertising> selectAdsByPOsitionId(Long positionId) {
		AdvertisingQuery advertisingQuery = new AdvertisingQuery();
		advertisingQuery.createCriteria().andPositionIdEqualTo(positionId);
		List<Advertising> ads = advertisingMapper.selectByExample(advertisingQuery);
		//填充广告位
		for (Advertising ad : ads) {
			ad.setPosition(positionMapper.selectByPrimaryKey(ad.getPositionId()));
		}
		return ads;
	}

	/**
	 * 保存广告
	 */
	@Override
	public void insertAd(Advertising advertising) {
		advertisingMapper.insertSelective(advertising);
	}

	@Override
	public String selectAdsBypositionId(Long positionId) {
		String ads = jedis.get("ads");
		if (ads==null) {
			AdvertisingQuery advertisingQuery = new AdvertisingQuery();
			advertisingQuery.createCriteria().andPositionIdEqualTo(positionId);
			List<Advertising> list  = advertisingMapper.selectByExample(advertisingQuery);
			ads = JsonUtils.parseObjectToJson(list);
			jedis.set("ads", ads);
		}
		return ads;
	}

}
