package top.linzeliang.tmall.mapper;

import java.util.List;
import top.linzeliang.tmall.pojo.PropertyValue;
import top.linzeliang.tmall.pojo.PropertyValueExample;

public interface PropertyValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExample(PropertyValueExample example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);
}