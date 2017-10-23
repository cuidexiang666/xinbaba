package cn.itcast.babasport.mapper.product;

import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.ColorQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ColorMapper {
    int countByExample(ColorQuery example);

    int deleteByExample(ColorQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(Color record);

    int insertSelective(Color record);

    List<Color> selectByExample(ColorQuery example);

    Color selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Color record, @Param("example") ColorQuery example);

    int updateByExample(@Param("record") Color record, @Param("example") ColorQuery example);

    int updateByPrimaryKeySelective(Color record);

    int updateByPrimaryKey(Color record);
}