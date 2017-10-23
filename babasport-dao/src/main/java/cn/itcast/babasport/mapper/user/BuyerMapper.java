package cn.itcast.babasport.mapper.user;

import cn.itcast.babasport.pojo.user.Buyer;
import cn.itcast.babasport.pojo.user.BuyerQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BuyerMapper {
    int countByExample(BuyerQuery example);

    int deleteByExample(BuyerQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(Buyer record);

    int insertSelective(Buyer record);

    List<Buyer> selectByExample(BuyerQuery example);

    Buyer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Buyer record, @Param("example") BuyerQuery example);

    int updateByExample(@Param("record") Buyer record, @Param("example") BuyerQuery example);

    int updateByPrimaryKeySelective(Buyer record);

    int updateByPrimaryKey(Buyer record);
}