package com.immooc.dao;

import com.immooc.vo.User;

import java.util.List;

public interface UserDao {
    User getUserByUserName(String username);

    List<String> getRolesByUserName(String username);

    List<String> getPermissionsByUserName(String username);
}
