package top.linzeliang.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.linzeliang.tmall.mapper.UserMapper;
import top.linzeliang.tmall.pojo.User;
import top.linzeliang.tmall.pojo.UserExample;
import top.linzeliang.tmall.service.UserService;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;


    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");
        return userMapper.selectByExample(example);
    }

    @Override
    public boolean isExist(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);

        List<User> users = userMapper.selectByExample(example);
        if (users.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public User get(String name, String password) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andNameEqualTo(name)
                .andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(example);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
