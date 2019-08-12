package com.hejie.tmall_ssm.test;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
  * @Program: tamll_ssm
  * @Description: 测试数据库连接
  * @Author: hejie
  * @Create: 2019/7/29
  */
public class TestTmall {

    public static void main(String args[]) {
        //读取properties
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration("jdbc.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        if (config == null) {
            return;
        }

        //准备分类测试数据：
        try {
            Class.forName(config.getString("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
            Connection con = DriverManager.getConnection(config.getString("jdbc.url"), config.getString("jdbc.username"), config.getString("jdbc.password"));
            Statement stat = con.createStatement()
        ) {

            stat.execute("delete from category");
            for (int i = 1; i <= 10; i++) {
                String sqlFormat = "insert into category values (null, '测试分类%d')";
                String sql = String.format(sqlFormat, i);
                System.out.println(sql);
                stat.execute(sql);
            }
            System.out.println("已经成功创建10条分类测试数据");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}