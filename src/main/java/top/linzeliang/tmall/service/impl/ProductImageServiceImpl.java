package top.linzeliang.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.linzeliang.tmall.mapper.ProductImageMapper;
import top.linzeliang.tmall.pojo.ProductImage;
import top.linzeliang.tmall.pojo.ProductImageExample;
import top.linzeliang.tmall.service.ProductImageService;

import java.util.List;

@Service("productImageServiceImpl")
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    @Qualifier("productImageMapper")
    private ProductImageMapper productImageMapper;

    @Override
    public void add(ProductImage pi) {
        productImageMapper.insert(pi);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ProductImage pi) {
        productImageMapper.updateByPrimaryKeySelective(pi);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductImage> list(int pid, String type) {
        ProductImageExample example = new ProductImageExample();
        example.createCriteria()
                .andPidEqualTo(pid)
                .andTypeEqualTo(type);
        example.setOrderByClause("id desc");

        return productImageMapper.selectByExample(example);
    }
}
