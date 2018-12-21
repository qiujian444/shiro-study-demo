package com.immooc.dao.impl;

import com.immooc.dao.UserDao;
import com.immooc.vo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<String> getRolesByUserName(String username) {
        String sql = "select role_name from user_roles where username = ?";
        return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            @Nullable
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_name");
            }
        });
    }

    public List<String> getPermissionsByUserName(String username) {
        String sql = "select permission from roles_permissions rp inner join user_roles ur on ur.role_name = rp.role_name where ur.username = ?";
        return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            @Nullable
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("permission");
            }
        });
    }

    public User getUserByUserName(String username) {
        String sql = "select username ,password from users where username = ?";
        List<User> list = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<User>() {
            @Nullable
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }
}
