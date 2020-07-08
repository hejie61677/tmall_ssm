package com.hejie.tmall_ssm.test;

import com.hejie.tmall_ssm.shiro.ShiroUtil;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Program: tmall_ssm
 * @Description: 测试权限管理
 * @Author: hejie
 * @Create: 2020-06-22 08:51
 */
public class TestShiro {

    public static void main(String[] args) {

        ShiroUtil shiroUtil = new ShiroUtil();

//        //users
//        User admin = new User();
//        admin.setName("hejie");
//        admin.setPassword("ddd");
//
//        User yeqiu = new User();
//        yeqiu.setName("yeqiu");
//        yeqiu.setPassword("yeqiu");
//
//        User other = new User();
//        other.setName("other");
//        other.setPassword("other");
//
//        List<User> users = new ArrayList<>();
//        users.add(admin);
//        users.add(yeqiu);
//        users.add(other);
//
//        //roles
//        String roleAdmin = "admin";
//        String roleProductManager ="productManager";
//
//        List<String> roles = new ArrayList<>();
//        roles.add(roleAdmin);
//        roles.add(roleProductManager);
//
//        //permissions
//        String permitProduct = "product";
//        String permitOrder = "order";
//
//        List<String> permissions = new ArrayList<>();
//        permissions.add(permitProduct);
//        permissions.add(permitOrder);
//
//        //登陆每个用户
//        for (User user : users) {
//            if(shiroUtil.login(user)) {
//                System.out.printf("%s \t登陆成功，用的密码是:<%s>\t%n", user.getName(), user.getPassword());
//            } else {
//                System.out.printf("%s \t登陆失败，用的密码是:<%s>\t%n", user.getName(), user.getPassword());
//            }
//        }
//
//        System.out.println("-------快乐的分割线------");
//
//        //判断能够登录的用户是否拥有某个角色
//        for (User user : users) {
//            for (String role : roles) {
//                if(shiroUtil.login(user)) {
//                    if(shiroUtil.hasRole(role)) {
//                        System.out.printf("%s\t拥有角色:<%s>\t%n", user.getName(), role);
//                    } else {
//                        System.out.printf("%s\t未拥有角色:<%s>\t%n", user.getName(), role);
//                    }
//                }
//            }
//        }
//
//        System.out.println("-------快乐的分割线------");
//
//        //判断能够登录的用户，是否拥有某种权限
//        for (User user : users) {
//            for (String permission : permissions) {
//                if(shiroUtil.login(user)) {
//                    if(shiroUtil.isPermitted(permission)) {
//                        System.out.printf("%s\t拥有权限:<%s>\t%n", user.getName(), permission);
//                    } else {
//                        System.out.printf("%s\t未拥有权限:<%s>\t%n", user.getName(), permission);
//                    }
//                }
//            }
//        }
//
//        System.out.println("-------快乐的分割线------");
//
//        for (User user : users) {
//            String name = user.getName();
//            System.out.printf("用户名<%s>,输入密码为<%s>,实际密码为<%s>%n", name, user.getPassword(), shiroUtil.getPassword(name));
//            Set<String> rs = shiroUtil.getRoles(name);
//            System.out.println("用户<" + name + ">拥有角色:" + rs);
//            Set<String> ps = shiroUtil.getPermissions(name);
//            System.out.println("用户<" + name + ">拥有权限:" + ps);
//            rs.clear();
//            ps.clear();
//        }

//        shiroUtil.createUser("gaizhonggai", "gaizhonggai");
//
//        User user = new User();
//        user.setName("gaizhonggai");
//        user.setPassword("gaizhonggai");
//
//        if (shiroUtil.login(user)) {
//            System.out.println("login success");
//        } else {
//            System.out.println("login failure");
//        }
        String password = "yexiu";
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String encodePassword = new SimpleHash("md5", password, salt, 2).toString();
        System.out.println(encodePassword);
        System.out.println(salt);

    }
}
