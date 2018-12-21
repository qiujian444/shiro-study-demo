package com.immoc.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring_demo");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }
    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        String sql = "select password from test_user where username = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
        subject.login(token);
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        //subject.checkRole("admin");
        //subject.checkPermission("update");

    }
}
