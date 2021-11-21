package top.linzeliang.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.linzeliang.tmall.pojo.Product;
import top.linzeliang.tmall.pojo.PropertyValue;
import top.linzeliang.tmall.service.ProductService;
import top.linzeliang.tmall.service.PropertyValueService;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyValueController {

    @Autowired
    @Qualifier("propertyValueServiceImpl")
    private PropertyValueService propertyValueService;

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(int pid, Model model) {
        Product p = productService.get(pid);
        propertyValueService.init(p);
        List<PropertyValue> pvs = propertyValueService.list(pid);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue pv) {
        propertyValueService.update(pv);
        return "success";
    }
}
