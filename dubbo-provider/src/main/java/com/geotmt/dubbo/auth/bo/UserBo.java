package com.geotmt.dubbo.auth.bo;

import com.geotmt.dubbo.auth.po.Permission;
import com.geotmt.dubbo.auth.po.Role;
import com.geotmt.dubbo.auth.po.User;

import java.util.List;

public interface UserBo {

    List<User> getAllUser(int pageNo, int limit);

    int getUserCount();

    User getUserInfo(int userId);

    int insertUser(User user);

    int deleteUser(int userId);

    int updateUser(User user);

    List<Role> getAllRole(int pageNo, int limit);

    Role getRoleInfo(int roleId);

    int insertRole(Role role);

    int deleteRole(int roleId);

    int updateRole(Role role);

    List<Permission> getAllPermission(int pageNo, int limit);

    int insertPermission(Permission permission);

    int deletePermission(int permissionId);

    int updatePermission(Permission permission);

    User findByUserName(String username);

    boolean canNotDeletePermission(int permissionId);

    boolean canNotDeleteRole(int roleId);

    int getPermissionCount();

    int getRoleCount();
}
