package top.linzeliang.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.linzeliang.tmall.pojo.Category;
import top.linzeliang.tmall.service.CategoryService;
import top.linzeliang.tmall.util.ImageUtil;
import top.linzeliang.tmall.util.Page;
import top.linzeliang.tmall.util.UploadedImageFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {

    @Autowired
    @Qualifier("categoryServiceImpl")
    private CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> cs = categoryService.list();
        PageInfo<Category> pageInfo = new PageInfo<>(cs);
        int total = (int)pageInfo.getTotal();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category category, HttpServletRequest request, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.add(category);
        File imageFolder = new File(request.getServletContext().getRealPath("/img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpServletRequest request) {
        categoryService.delete(id);

        File imageFolder = new File(request.getServletContext().getRealPath("/img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();

        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String get(int id, Model model) {
        Category c = categoryService.get(id);
        model.addAttribute("c", c);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category category, UploadedImageFile uploadedImageFile, HttpServletRequest request) throws IOException {
        categoryService.update(category);

        File imageFolder = new File(request.getServletContext().getRealPath("/img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        return "redirect:/admin_category_list";
    }
}
