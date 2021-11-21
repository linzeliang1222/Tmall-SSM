package top.linzeliang.tmall.service;

import top.linzeliang.tmall.pojo.Order;
import top.linzeliang.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {

    void add(OrderItem orderItem);

    void delete(int id);

    void update(OrderItem orderItem);

    OrderItem get(int id);

    List<OrderItem> list();

    void fill(List<Order> os);

    void fill(Order o);

    int getSaleCount(int pid);

    List<OrderItem> listByUser(int uid);
}
