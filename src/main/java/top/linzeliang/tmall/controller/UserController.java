package top.linzeliang.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.linzeliang.tmall.pojo.User;
import top.linzeliang.tmall.service.UserService;
import top.linzeliang.tmall.util.Page;

import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    @RequestMapping("admin_user_list")
    public String list(Page page, Model model) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> us = userService.list();
        PageInfo<User> pageInfo = new PageInfo<>(us);

        int total = (int)pageInfo.getTotal();
        page.setTotal(total);

        model.addAttribute("page", page);
        model.addAttribute("us", us);

        return "admin/listUser";
    }

}
