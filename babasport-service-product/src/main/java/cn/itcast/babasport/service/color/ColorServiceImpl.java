package cn.itcast.babasport.service.color;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.product.ColorMapper;
import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.ColorQuery;
import cn.itcast.babasport.pojo.product.ColorQuery.Criteria;

@Service("colorService")
public class ColorServiceImpl implements ColorService{

	@Resource
	private ColorMapper colorMapper;
	
	@Override
	public List<Color> selectColorList() {
		//封装查询条件
		ColorQuery colorQuery = new ColorQuery();
		Criteria criteria = colorQuery.createCriteria().andParentIdNotEqualTo(0L);
		//执行查询操作
		List<Color> colors = colorMapper.selectByExample(colorQuery);
		return colors;
	}

}
