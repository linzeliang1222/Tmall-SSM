package top.linzeliang.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.linzeliang.tmall.pojo.Product;
import top.linzeliang.tmall.pojo.ProductImage;
import top.linzeliang.tmall.service.ProductImageService;
import top.linzeliang.tmall.service.ProductService;
import top.linzeliang.tmall.util.ImageUtil;
import top.linzeliang.tmall.util.UploadedImageFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {

    @Autowired
    @Qualifier("productImageServiceImpl")
    private ProductImageService productImageService;

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;


    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, UploadedImageFile uploadedImageFile, HttpServletRequest request) {
        productImageService.add(productImage);

        String fileName = productImage.getId() + ".jpg";
        String imageFolder;
        String imageFolderSmall = null;
        String imageFolderMiddle = null;

        // 创建路径
        if (ProductImageService.TYPE_DETAIL.equals(productImage.getType())) {
            imageFolder = request.getServletContext().getRealPath("/img/productDetail");
        } else {
            imageFolder = request.getServletContext().getRealPath("/img/productSingle");
            imageFolderSmall = request.getServletContext().getRealPath("/img/productSingle_small");
            imageFolderMiddle = request.getServletContext().getRealPath("/img/productSingle_middle");
        }

        File file = new File(imageFolder, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage image = ImageUtil.change2jpg(file);
            ImageIO.write(image, "jpg", file);

            if (ProductImageService.TYPE_SINGLE.equals(productImage.getType())) {
                File fileSmall = new File(imageFolderSmall, fileName);
                File fileMiddle = new File(imageFolderMiddle, fileName);

                ImageUtil.resizeImage(file, 56, 56, fileSmall);
                ImageUtil.resizeImage(file, 217, 217, fileMiddle);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:admin_productImage_list?pid=" + productImage.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpServletRequest request) {
        ProductImage productImage = productImageService.get(id);

        String fileName = id + ".jpg";
        String imageFolder;
        String imageFolderSmall;
        String imageFolderMiddle;

        if (ProductImageService.TYPE_SINGLE.equals(productImage.getType())) {
            imageFolder = request.getServletContext().getRealPath("/img/productSingle");
            imageFolderMiddle = request.getServletContext().getRealPath("/img/productSingle_middle");
            imageFolderSmall = request.getServletContext().getRealPath("/img/productSingle_small");
            File file = new File(imageFolder, fileName);
            File fileSmall = new File(imageFolderSmall, fileName);
            File fileMiddle = new File(imageFolderMiddle, fileName);

            file.delete();
            fileSmall.delete();
            fileMiddle.delete();
        } else {
            imageFolder = request.getServletContext().getRealPath("/img/productDetail");
            File file = new File(imageFolder, fileName);
            file.delete();
        }

        productImageService.delete(id);


        return "redirect:admin_productImage_list?pid=" + productImage.getPid();
    }

    @RequestMapping("admin_productImage_list")
    public String list(Model model, int pid) {
        Product product = productService.get(pid);
        List<ProductImage> productImageDetail = productImageService.list(pid, ProductImageService.TYPE_DETAIL);
        List<ProductImage> productImageSingle = productImageService.list(pid, ProductImageService.TYPE_SINGLE);

        model.addAttribute("p", product);
        model.addAttribute("pisSingle", productImageSingle);
        model.addAttribute("pisDetail", productImageDetail);

        return "admin/listProductImage";
    }

}
