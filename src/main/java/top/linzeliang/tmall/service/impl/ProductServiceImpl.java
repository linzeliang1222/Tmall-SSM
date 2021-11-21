package top.linzeliang.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.linzeliang.tmall.mapper.CategoryMapper;
import top.linzeliang.tmall.mapper.ProductImageMapper;
import top.linzeliang.tmall.mapper.ProductMapper;
import top.linzeliang.tmall.pojo.Category;
import top.linzeliang.tmall.pojo.Product;
import top.linzeliang.tmall.pojo.ProductExample;
import top.linzeliang.tmall.pojo.ProductImage;
import top.linzeliang.tmall.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;


@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

    @Autowired
    @Qualifier("categoryServiceImpl")
    private CategoryService categoryService;

    @Autowired
    @Qualifier("productMapper")
    private ProductMapper productMapper;

    @Autowired
    @Qualifier("productImageServiceImpl")
    private ProductImageService productImageService;

    @Autowired
    @Qualifier("orderItemServiceImpl")
    private OrderItemService orderItemService;

    @Autowired
    @Qualifier("reviewServiceImpl")
    private ReviewService reviewService;


    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        this.setCategory(product);
        this.setFirstProductImage(product);
        return product;
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");

        List<Product> products = productMapper.selectByExample(example);
        this.setCategory(products);
        this.setFirstProductImage(products);

        return products;
    }

    public void setCategory(Product product) {
        int cid = product.getCid();
        Category category = categoryService.get(cid);
        product.setCategory(category);
    }

    public void setCategory(List<Product> products) {
        for (Product product : products) {
            setCategory(product);
        }
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> pisSingle = productImageService.list(product.getId(), ProductImageService.TYPE_SINGLE);
        if (!pisSingle.isEmpty()) {
            product.setFirstProductImage(pisSingle.get(0));
        }
    }

    public void setFirstProductImage(List<Product> products) {
        for (Product product : products) {
            setFirstProductImage(product);
        }
    }

    @Override
    public void fill(List<Category> categorys) {
        for (Category category : categorys) {
            this.fill(category);
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> products = this.list(category.getId());
        category.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> categorys) {
        int productNumberEachRow=8;
        for (Category category : categorys) {
            List<Product> products = this.list(category.getId());
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size =  i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product p) {
        int saleCount = orderItemService.getSaleCount(p.getId());
        p.setSaleCount(saleCount);
        int reviewCount = reviewService.getCount(p.getId());
        p.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p : ps) {
            setSaleAndReviewNumber(p);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(example);
        this.setFirstProductImage(products);
        this.setCategory(products);
        return products;
    }
}
