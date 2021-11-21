package top.linzeliang.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.linzeliang.tmall.mapper.OrderMapper;
import top.linzeliang.tmall.pojo.Order;
import top.linzeliang.tmall.pojo.OrderExample;
import top.linzeliang.tmall.pojo.OrderItem;
import top.linzeliang.tmall.pojo.User;
import top.linzeliang.tmall.service.OrderItemService;
import top.linzeliang.tmall.service.OrderService;
import top.linzeliang.tmall.service.UserService;

import java.util.List;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Autowired
    @Qualifier("orderMapper")
    private OrderMapper orderMapper;

    @Autowired
    @Qualifier("orderItemServiceImpl")
    private OrderItemService orderItemService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order order, List<OrderItem> orderItems) {
        float total = 0;
        add(order);

        if (false) {
            throw new RuntimeException();
        }

        for (OrderItem orderItem : orderItems) {
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        return total;
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        setUser(orders);
        return orders;
    }

    public void setUser(List<Order> os) {
        for (Order o : os) {
            setUser(o);
        }
    }

    public void setUser(Order o) {
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }

    @Override
    public List<Order> list(int uid, String excludedStatus) {
        OrderExample example = new OrderExample();
        example.createCriteria()
                .andUidEqualTo(uid)
                .andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        setUser(orders);
        return orders;
    }
}
