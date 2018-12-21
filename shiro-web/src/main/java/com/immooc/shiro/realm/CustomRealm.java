package com.immooc.shiro.realm;

import com.immooc.dao.UserDao;
import com.immooc.vo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.*;

public class CustomRealm extends AuthorizingRealm {
    Map<String,String> userMap = new HashMap<String, String>(16);
    {
        userMap.put("Mark","283538989cef48f3d7d8a1c1bdf2008f");
    }

    @Resource
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        Set<String> roles = getRolesByUserName(username);
        Set<String> permissions = getPermissionsByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String username) {
        /*Set<String> sets = new HashSet<String>();
        sets.add("user:update");
        sets.add("user:delete");
        return sets;*/
        List<String> list = userDao.getPermissionsByUserName(username);
        Set<String> sets = new HashSet<String>(list);
        return sets;
    }

    private Set<String> getRolesByUserName(String username) {
        /*Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;*/
        List<String> list = userDao.getRolesByUserName(username);
        Set<String> sets = new HashSet<String>(list);
        return sets;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = getPasswordByUserName(username);
        if(password == null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,"sdfsdf");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));
        return simpleAuthenticationInfo;
    }

    private String getPasswordByUserName(String username) {
        //return userMap.get(username);
        User user = userDao.getUserByUserName(username);
        if(user != null){
            return user.getPassword();
        }
        return null;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","Mark");
        System.out.println(md5Hash);
    }
}
