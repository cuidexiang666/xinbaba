package cn.itcast.babasport.service.ad;

import java.util.List;

import cn.itcast.babasport.pojo.ad.Advertising;
import cn.itcast.babasport.pojo.ad.Position;

public interface AdService {

	/**
	 * 查询广告位
	 * @param parentId
	 * @return
	 */
	public List<Position> selectPositionListByParentId(Long parentId);
	
	/**
	 * 查询指定广告位下的广告列表
	 * @param positionId
	 * @return
	 */
	public List<Advertising> selectAdsByPOsitionId(Long positionId);
	
	/**
	 * 保存广告
	 */
	public void insertAd(Advertising advertising);
	
	/**
	 * 前台系统大广告轮播
	 * @param positionId
	 * @return
	 */
	public String selectAdsBypositionId(Long positionId);
	
}
