package com.hejie.tmall_ssm.util;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: MybatisGenerator插件是Mybatis官方提供的，这个插件存在一个固有的Bug，即当第一次生成了CategoryMapper.xml之后，再次运行会导致CategoryMapper.xml生成重复内容，而影响正常的运行。
 *                  为了解决这个问题，需要自己写一个小插件类OverIsMergeablePlugin
 * @Author: hejie
 * @Create: 2019-06-25 14:46
 */
public class OverIsMergeablePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
