package com.hejie.tmall_ssm.pojo;

/**
 * @program: tmall_ssm
 * @description: User拓展类
 * @author: hejie
 * @create: 2019-07-30 16:20
 */
public class UserExpand extends User {

    /**
     *  匿名显示
     */
    public String getAnonymousName() {
        int length = this.getName().length();

        if (null == this.getName()) {
            return null;
        }

        if (length <= 1) {
            return "*";
        } else if (length == 2) {
            return this.getName().substring(0, 1) + "*";
        } else {
            char[] names = this.getName().toCharArray();

            for (int i = 1; i < names.length - 1; i++) {
                names[i] = '*';
            }

            return String.valueOf(names);
        }
    }

    public void setUser(User user) {
        this.setName(user.getName());
        this.setId(user.getId());
        this.setPassword(user.getPassword());
    }

}
