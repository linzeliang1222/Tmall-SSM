package top.linzeliang.tmall.service;

import top.linzeliang.tmall.pojo.User;

import java.util.List;

public interface UserService {
    void add(User user);

    void delete(int id);

    void update(User user);

    User get(int id);

    List<User> list();

    boolean isExist(String name);

    User get(String name, String password);
}
