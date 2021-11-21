package top.linzeliang.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.linzeliang.tmall.mapper.ReviewMapper;
import top.linzeliang.tmall.pojo.Review;
import top.linzeliang.tmall.pojo.ReviewExample;
import top.linzeliang.tmall.pojo.User;
import top.linzeliang.tmall.service.ReviewService;
import top.linzeliang.tmall.service.UserService;

import java.util.List;

@Service("reviewServiceImpl")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    @Qualifier("reviewMapper")
    private ReviewMapper reviewMapper;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Review> list(int pid) {
        ReviewExample example =  new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id  desc");
        List<Review> reviews = reviewMapper.selectByExample(example);
        setUser(reviews);
        return reviews;
    }

    @Override
    public int getCount(int pid) {
        return this.list(pid).size();
    }

    public void setUser(List<Review> reviews) {
        for (Review review : reviews) {
            this.setUser(review);
        }
    }

    public void setUser(Review review)  {
        int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }
}
