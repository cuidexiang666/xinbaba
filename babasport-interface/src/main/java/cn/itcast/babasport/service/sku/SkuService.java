package cn.itcast.babasport.service.sku;

import java.util.List;

import cn.itcast.babasport.pojo.product.Sku;

public interface SkuService {

	/**
	 * 根据ID查询库存
	 * @param productId
	 * @return
	 */
	public List<Sku> selectSkuByProductId(Long productId);
	
	/**
	 * 保存库存操作
	 * @param sku
	 */
	public void saveSkuById(Sku sku);
}
