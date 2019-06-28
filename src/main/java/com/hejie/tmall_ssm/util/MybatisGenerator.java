package com.hejie.tmall_ssm.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: tmall_ssm
 * @description: 逆向工程生产pojo,mapper,xml
 * @author: hejie
 * @create: 2019-06-25 15:25
 */
public class MybatisGenerator {

    public static void main(String[] args) throws Exception {

        String endDay = "2019-06-25";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(endDay);
        Date now = new Date();

        if(now.getYear() == date.getYear() && now.getMonth() == date.getMonth() && now.getDay() == date.getDay()) {

            List<String> warnings = new ArrayList<String>();
            boolean overWrite = true;
            InputStream inputStream = MyBatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").openStream();
            ConfigurationParser configurationParser = new ConfigurationParser(warnings);
            Configuration configuration = configurationParser.parseConfiguration(inputStream);
            inputStream.close();
            DefaultShellCallback defaultShellCallback = new DefaultShellCallback(overWrite);
            MyBatisGenerator mybatisGenerator = new MyBatisGenerator(configuration, defaultShellCallback, warnings);
            mybatisGenerator.generate(null);
            System.out.println("生成代码成功，只能执行一次，以后执行会覆盖掉mapper,pojo,xml 等文件上做的修改");

        } else {

            System.err.println("——————未成成功运行——————");
            System.err.println("——————未成成功运行——————");
            System.err.println("本程序具有破坏作用，应该只运行一次，如果必须要再运行，需要修改today变量为今天，如:" + simpleDateFormat.format(new Date()));
            return;
        }
    }
}
