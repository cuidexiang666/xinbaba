package cn.itcast.babasport.service.cms;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.product.ColorMapper;
import cn.itcast.babasport.mapper.product.ProductMapper;
import cn.itcast.babasport.mapper.product.SkuMapper;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.pojo.product.SkuQuery;
import cn.itcast.babasport.service.cms.CmsService;

@Service("cmsService")
public class CmsServiceImpl implements CmsService {

	@Resource
	private ProductMapper productMapper;
	
	@Resource
	private SkuMapper skuMapper;
	
	@Resource
	private ColorMapper colorMapper;
	
	/**
	 * 根据商品ID查询商品
	 */
	@Override
	public Product selectProductById(Long id) {
		Product product = productMapper.selectByPrimaryKey(id);
		return product;
	}

	/**
	 * 根据商品ID查询库存大于0的信息
	 */
	@Override
	public List<Sku> selectSkusByProIdWithStorkMoreZero(Long productId) {
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(productId).andStockGreaterThan(0);
		List<Sku> skus = skuMapper.selectByExample(skuQuery);
		for (Sku sku : skus) {
			sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		}
		return skus;
	}

}
