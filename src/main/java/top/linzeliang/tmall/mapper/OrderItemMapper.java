package top.linzeliang.tmall.mapper;

import java.util.List;
import top.linzeliang.tmall.pojo.OrderItem;
import top.linzeliang.tmall.pojo.OrderItemExample;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    List<OrderItem> selectByExample(OrderItemExample example);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}