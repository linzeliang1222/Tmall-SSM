package top.linzeliang.tmall.comparator;

import top.linzeliang.tmall.pojo.Product;

import java.util.Comparator;

public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount() - o1.getReviewCount();
    }
}