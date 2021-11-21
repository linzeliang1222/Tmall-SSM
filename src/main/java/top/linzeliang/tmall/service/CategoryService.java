package top.linzeliang.tmall.service;

import top.linzeliang.tmall.pojo.Category;
import top.linzeliang.tmall.util.Page;

import java.util.List;

public interface CategoryService {

    List<Category> list();

    void add(Category category);

    void delete(int cid);

    Category get(int cid);

    void update(Category category);
}
