package top.linzeliang.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.linzeliang.tmall.pojo.Order;
import top.linzeliang.tmall.pojo.OrderItem;
import top.linzeliang.tmall.service.OrderItemService;
import top.linzeliang.tmall.service.OrderService;
import top.linzeliang.tmall.util.Page;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {

    @Autowired
    @Qualifier("orderServiceImpl")
    private OrderService orderService;

    @Autowired
    @Qualifier("orderItemServiceImpl")
    private OrderItemService orderItemService;


    @RequestMapping("admin_order_list")
    public String list(Page page, Model model) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> os = orderService.list();
        PageInfo<Order> pageInfo = new PageInfo<>(os);

        int total = (int)pageInfo.getTotal();
        page.setTotal(total);

        orderItemService.fill(os);

        model.addAttribute("page", page);
        model.addAttribute("os", os);

        return "admin/listOrder";
    }

    @RequestMapping("admin_order_delivery")
    public String delivery(Order order)  {
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);
        return "redirect:admin_order_list";
    }
}
