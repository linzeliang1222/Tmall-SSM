package top.linzeliang.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.linzeliang.tmall.pojo.Category;
import top.linzeliang.tmall.pojo.Property;
import top.linzeliang.tmall.service.CategoryService;
import top.linzeliang.tmall.service.PropertyService;
import top.linzeliang.tmall.util.Page;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyController {

    @Autowired
    @Qualifier("categoryServiceImpl")
    CategoryService categoryService;

    @Autowired
    @Qualifier("propertyServiceImpl")
    PropertyService propertyService;


    @RequestMapping("admin_property_add")
    public String add(Property property) {
        propertyService.add(property);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String delete(int id) {
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String edit(Model model, int id) {
        Property property = propertyService.get(id);
        Category category = categoryService.get(property.getCid());
        property.setCategory(category);
        model.addAttribute("p", property);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String update(Property property) {
        propertyService.update(property);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_list")
    public String list(int cid, Model model, Page page) {
        Category c = categoryService.get(cid);

        // 分页查询
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> ps = propertyService.list(cid);
        PageInfo<Property> pageInfo = new PageInfo<>(ps);

        int total = (int)pageInfo.getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + c.getId());

        model.addAttribute("ps", ps);
        model.addAttribute("c", c);
        model.addAttribute("page", page);

        return "admin/listProperty";
    }

}
