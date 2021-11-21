package top.linzeliang.tmall.mapper;

import java.util.List;
import top.linzeliang.tmall.pojo.Category;
import top.linzeliang.tmall.pojo.CategoryExample;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}