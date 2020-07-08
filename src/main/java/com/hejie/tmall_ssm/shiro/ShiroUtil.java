package com.hejie.tmall_ssm.shiro;

import com.hejie.tmall_ssm.pojo.User;
import com.hejie.tmall_ssm.util.ConnDbUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Program: tmall_ssm
 * @Description: 基于shiro框架工具类
 * @Author: hejie
 * @Create: 2020-06-19 15:19
 */
public class ShiroUtil {

    private String return_code = "0000";

    /**
     * @description: 判断用户是否拥有此角色
     * @param role 角色
     * @return true/false
     */
    public boolean hasRole(String role) {
        Subject subject = getSubject();
        return subject.hasRole(role);
    }

    /**
     * @description: 判断用户是否拥有此权限
     * @param permit 权限
     * @return true/false
     */
    public boolean isPermitted(String permit) {
        Subject subject = getSubject();
        return subject.isPermitted(permit);
    }

    /**
     * @description: 获取subject对象
     * @return Subject
     */
    private Subject getSubject() {
        //加载配置文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //获取安全管理者实例
        SecurityManager securityManager = factory.getInstance();
        //将安全管理者放入全局对象
        SecurityUtils.setSecurityManager(securityManager);
        //生成Subject
        return SecurityUtils.getSubject();
    }

    /**
     * @description: 登录验证
     * @param user 用户
     * @return true/false
     */
    public boolean login(User user) {
        Subject subject = getSubject();
        //如果已经登录过了，退出
        if(subject.isAuthenticated()) {
            subject.logout();
        }

        //实例化身份令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return false;//验证失败
        }
        return subject.isAuthenticated();
    }

    /**
      * @Description: 用户注册
      * @Param: name 用户名 password 密码
      * @Return: return_code
      * @Author: hejie
      * @Date: 2020/7/2
      */
    public String createUser(String name, String password) {
        String sql = "insert into user values (null, ?, ?, ?)";
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String encodePassword = new SimpleHash("md5", password, salt, 2).toString();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ConnDbUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, encodePassword);
            ps.setString(3, salt);
            ps.execute();

        } catch (SQLException e) {
            return_code = "errInsert";
            e.printStackTrace();
        } finally {
            ConnDbUtil.closeU(ps, con);
        }
        return return_code;
    }

    /**
     * @Description: 获取用户信息
     * @Param: name 用户名 password 密码
     * @Return: user
     * @Author: hejie
     * @Date: 2020/7/2
     */
    public User getUser(String name) {
        String sql = "select * from user where name = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            con = ConnDbUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setMd5salt(rs.getString("md5salt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnDbUtil.close(rs, ps, con);
        }

        return user;
    }

    /**
     * @description: 获取用户密码
     * @param name 用户名
     * @return password 密码
     */
    public String getPassword(String name) {
        String sql = "select password from user where name = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnDbUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
               return rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnDbUtil.close(rs, ps, con);
        }

        return null;
    }

    /**
     * @description: 获取用户所拥有的角色
     * @param name 用户名
     * @return Set<String> 角色列表
     */
    public Set<String> getRoles(String name) {
        boolean defaultValue = true;

        if (defaultValue) {
            Set<String> rolesDef = new HashSet<>();
            rolesDef.add("Admin");
            return rolesDef;
        } else {
            String sql = "select r.name from user u"
                    + " left join user_role ur on u.id = ur.uid"
                    + " left join role r on ur.rid = r.id"
                    + " where u.name = ?";
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                con = ConnDbUtil.getConnection();
                ps = con.prepareStatement(sql);
                ps.setString(1, name);
                rs = ps.executeQuery();
                Set<String> roles = new HashSet<>();

                while (rs.next()) {
                    roles.add(rs.getString("name"));
                }

                return roles;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnDbUtil.close(rs, ps, con);
            }

            return null;
        }
    }

    /**
     * @description: 获取用户所拥有的权限
     * @param name 用户名
     * @return Set<String> 权限列表
     */
    public Set<String> getPermissions(String name) {
        boolean defaultValue = true;

        if (defaultValue) {
            Set<String> permissionsDef = new HashSet<>();
            permissionsDef.add("admin");
            return permissionsDef;
        } else {
            String sql = "select p.name from user u"
                    + " left join user_role ur on u.id = ur.uid"
                    + " left join role r on ur.rid = r.id"
                    + " left join role_permission rp on r.id = rp.rid"
                    + " left join permission p on rp.pid = p.id"
                    + " where u.name = ?";
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                con = ConnDbUtil.getConnection();
                ps = con.prepareStatement(sql);
                ps.setString(1, name);
                rs = ps.executeQuery();
                Set<String> permissions = new HashSet<>();

                while (rs.next()) {
                    permissions.add(rs.getString("name"));
                }

                return permissions;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnDbUtil.close(rs, ps, con);
            }

            return null;
        }
    }
}
