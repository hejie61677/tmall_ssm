package com.hejie.tmall_ssm.shiro;

import com.hejie.tmall_ssm.pojo.User;
import com.hejie.tmall_ssm.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @Program: tmall_ssm
 * @Description:
 * @Author: hejie
 * @Create: 2020-06-23 17:24
 */
public class DatabaseRealm extends AuthorizingRealm {
    private ShiroUtil shiroUtil = new ShiroUtil();

    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = shiroUtil.getRoles(username);
        Set<String> permissions = shiroUtil.getPermissions(username);

        //授权对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //设置角色和权限
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户/密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getPrincipal().toString();
        String password = new String(token.getPassword());
        //获取数据库中用户信息
        User user = userService.get(username);
        String passwordEncoded = new SimpleHash("md5", password, user.getMd5salt(), 2).toString();
        //进行认证
        if (!passwordEncoded.equals(user.getPassword())) {
            throw new AuthenticationException();
        }

        //认证对象
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
