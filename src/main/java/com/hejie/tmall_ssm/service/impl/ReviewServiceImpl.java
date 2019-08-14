package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.ReviewMapper;
import com.hejie.tmall_ssm.pojo.Order;
import com.hejie.tmall_ssm.pojo.Review;
import com.hejie.tmall_ssm.pojo.ReviewExample;
import com.hejie.tmall_ssm.pojo.ReviewExpand;
import com.hejie.tmall_ssm.service.OrderService;
import com.hejie.tmall_ssm.service.ReviewService;
import com.hejie.tmall_ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: ReviewService接口实现类
 * @Author: hejie
 * @Create: 2019-07-30 16:39
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public void add(Review review, Order order) {
        orderService.update(order);
        add(review);
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
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.createCriteria().andPidEqualTo(pid);
        reviewExample.setOrderByClause("id asc");

        return reviewMapper.selectByExample(reviewExample);
    }

    @Override
    public List<ReviewExpand> listE(int pid) {
        List<Review> reviews = list(pid);
        List<ReviewExpand> reviewExpands = new ArrayList<>();
        ReviewExpand reviewExpand;

        for (Review review : reviews) {
            reviewExpand = new ReviewExpand();
            reviewExpand.setReview(review);
            reviewExpands.add(reviewExpand);
        }

        setUser(reviewExpands);

        return reviewExpands;
    }

    private void setUser(List<ReviewExpand> reviewExpands) {
        for (ReviewExpand reviewExpand : reviewExpands) {
            setUser(reviewExpand);
        }
    }

    private void setUser(ReviewExpand reviewExpand) {
        reviewExpand.setUser(userService.get(reviewExpand.getUid()));
        reviewExpand.setUserExpand(userService.getE(reviewExpand.getUid()));
    }

    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }
}
