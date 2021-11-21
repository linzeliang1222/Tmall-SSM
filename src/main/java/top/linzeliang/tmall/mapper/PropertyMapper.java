package top.linzeliang.tmall.mapper;

import java.util.List;
import top.linzeliang.tmall.pojo.Property;
import top.linzeliang.tmall.pojo.PropertyExample;

public interface PropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}