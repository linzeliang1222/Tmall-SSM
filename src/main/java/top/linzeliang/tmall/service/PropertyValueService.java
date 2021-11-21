package top.linzeliang.tmall.service;

import top.linzeliang.tmall.pojo.Product;
import top.linzeliang.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    void init(Product p);

    void update(PropertyValue pv);

    PropertyValue get(int ptid, int pid);

    List<PropertyValue> list(int pid);
}
