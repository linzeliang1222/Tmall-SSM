package top.linzeliang.tmall.service;

import top.linzeliang.tmall.pojo.Order;
import top.linzeliang.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order order);

    float add(Order order, List<OrderItem> orderItems);

    void delete(int id);

    void update(Order order);

    Order get(int id);

    List<Order> list();

    List<Order> list(int uid, String excludedStatus);
}
