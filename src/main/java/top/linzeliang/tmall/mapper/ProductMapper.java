package top.linzeliang.tmall.mapper;

import java.util.List;
import top.linzeliang.tmall.pojo.Product;
import top.linzeliang.tmall.pojo.ProductExample;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}