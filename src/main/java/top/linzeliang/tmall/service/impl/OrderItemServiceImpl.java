package top.linzeliang.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import top.linzeliang.tmall.mapper.OrderItemMapper;
import top.linzeliang.tmall.mapper.UserMapper;
import top.linzeliang.tmall.pojo.Order;
import top.linzeliang.tmall.pojo.OrderItem;
import top.linzeliang.tmall.pojo.OrderItemExample;
import top.linzeliang.tmall.pojo.Product;
import top.linzeliang.tmall.service.OrderItemService;
import top.linzeliang.tmall.service.ProductService;

import java.util.List;

@Service("orderItemServiceImpl")
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    @Qualifier("orderItemMapper")
    private OrderItemMapper orderItemMapper;

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;


    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
    }

    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            this.fill(o);
        }
    }

    @Override
    public void fill(Order o) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);

        float total = 0;
        int totalNumber = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            totalNumber += orderItem.getNumber();
        }

        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(orderItems);
    }

    public void setProduct(OrderItem oi) {
        int pid = oi.getPid();
        Product product = productService.get(pid);
        oi.setProduct(product);
    }

    public void setProduct(List<OrderItem> ois) {
        for (OrderItem orderItem : ois) {
            this.setProduct(orderItem);
        }
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria()
                .andPidEqualTo(pid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        int result = 0;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOid() != null) {
                result += orderItem.getNumber();
            }
        }
        return result;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        this.setProduct(orderItems);
        return orderItems;
    }
}
