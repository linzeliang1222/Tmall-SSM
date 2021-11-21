package top.linzeliang.tmall.service;

import top.linzeliang.tmall.pojo.Category;
import top.linzeliang.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);

    void delete(int id);

    void update(Product product);

    Product get(int id);

    List<Product> list(int cid);

    void setFirstProductImage(Product product);

    void fill(List<Category> categorys);

    void fill(Category category);

    void fillByRow(List<Category> categorys);

    void setSaleAndReviewNumber(Product p);

    void setSaleAndReviewNumber(List<Product> ps);

    List<Product> search(String keyword);
}
