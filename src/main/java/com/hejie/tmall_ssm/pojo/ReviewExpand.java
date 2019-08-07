package com.hejie.tmall_ssm.pojo;

/**
 * @Program: tmall_ssm
 * @Description: Review拓展类
 * @Author: hejie
 * @Create: 2019-07-30 16:12
 */
public class ReviewExpand extends Review {

    //评论用户
    private User user;

    //评论用户拓展
    private UserExpand userExpand;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserExpand getUserExpand() {
        return userExpand;
    }

    public void setUserExpand(UserExpand userExpand) {
        this.userExpand = userExpand;
    }

    public void setReview(Review review) {
        this.setContent(review.getContent());
        this.setCreate_date(review.getCreate_date());
        this.setId(review.getId());
        this.setPid(review.getPid());
        this.setUid(review.getUid());
    }
}
