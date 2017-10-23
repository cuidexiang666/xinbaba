package cn.itcast.babasport.service.cms;

import java.util.List;

import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.Sku;

public interface CmsService {

	/**
	 * 根据商品ID查询商品
	 * @param id
	 * @return
	 */
	public Product selectProductById(Long id);
	
	/**
	 * 根据商品ID查询库存大于0的信息
	 * @param productId
	 * @return
	 */
	public List<Sku> selectSkusByProIdWithStorkMoreZero(Long productId);
}
