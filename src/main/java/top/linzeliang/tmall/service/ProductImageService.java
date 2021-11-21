package top.linzeliang.tmall.service;

import top.linzeliang.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {

    String TYPE_SINGLE = "type_single";
    String TYPE_DETAIL = "type_detail";

    void add(ProductImage pi);

    void delete(int id);

    void update(ProductImage pi);

    ProductImage get(int id);

    List<ProductImage> list(int pid, String type);
}
