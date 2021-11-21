package top.linzeliang.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.linzeliang.tmall.mapper.CategoryMapper;
import top.linzeliang.tmall.pojo.Category;
import top.linzeliang.tmall.pojo.CategoryExample;
import top.linzeliang.tmall.service.CategoryService;
import top.linzeliang.tmall.util.Page;

import java.util.List;

@Service("categoryServiceImpl")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    @Qualifier("categoryMapper")
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> list() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("id desc");
        return categoryMapper.selectByExample(example);
    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void delete(int cid) {
        categoryMapper.deleteByPrimaryKey(cid);
    }

    @Override
    public Category get(int cid) {
        return categoryMapper.selectByPrimaryKey(cid);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }
}
