package cn.itcast.babasport.service.color;

import java.util.List;

import cn.itcast.babasport.pojo.product.Color;

public interface ColorService {

	/**
	 * 查询所有颜色信息
	 * @return
	 */
	public List<Color> selectColorList();
}
