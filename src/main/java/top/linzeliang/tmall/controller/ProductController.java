package top.linzeliang.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.linzeliang.tmall.pojo.Category;
import top.linzeliang.tmall.pojo.Product;
import top.linzeliang.tmall.service.CategoryService;
import top.linzeliang.tmall.service.ProductService;
import top.linzeliang.tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    @Qualifier("categoryServiceImpl")
    private CategoryService categoryService;

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    @RequestMapping("admin_product_add")
    public String add(Product product) {
        product.setCreateDate(new Date());
        productService.add(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(Model model, int id) {
        Product product = productService.get(id);
        model.addAttribute("p", product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product product) {
        productService.update(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page) {
        Category category = categoryService.get(cid);

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> products = productService.list(cid);
        PageInfo<Product> pageInfo = new PageInfo<>(products);

        int total = (int)pageInfo.getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + category.getId());

        model.addAttribute("c", category);
        model.addAttribute("ps", products);
        model.addAttribute("page", page);

        return "admin/listProduct";
    }
}
