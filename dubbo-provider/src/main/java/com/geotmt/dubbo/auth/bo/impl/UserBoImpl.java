package com.geotmt.dubbo.auth.bo.impl;

import com.geotmt.dubbo.auth.bo.UserBo;
import com.geotmt.dubbo.auth.mapper.PermissionMapper;
import com.geotmt.dubbo.auth.mapper.RoleMapper;
import com.geotmt.dubbo.auth.mapper.UserMapper;
import com.geotmt.dubbo.auth.po.Permission;
import com.geotmt.dubbo.auth.po.Role;
import com.geotmt.dubbo.auth.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserBoImpl implements UserBo {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<User> getAllUser(int pageNo,int limit) {
        List<User> users = userMapper.findAllUser(pageNo*limit,limit);
        if(users!=null&&!users.isEmpty()){
            List<Role> roles = roleMapper.findRoleByUserIds(users.stream().mapToInt(User::getUserId).toArray());
            if(roles!=null&&!roles.isEmpty()){
                Map<Integer,List<Role>> map = new HashMap<>();
                roles.forEach(role -> {
                    map.computeIfAbsent(role.getUserId(), k -> new ArrayList<>());
                    map.get(role.getUserId()).add(role);
                });
                users.forEach(user -> user.setRoles(map.get(user.getUserId())));
            }
        }
        return users;
    }

    @Override
    public int getUserCount() {
        return userMapper.getUserCount();
    }

    @Override
    public User getUserInfo(int userId) {
        User user = userMapper.findByUserId(userId);
        if(user!=null){
            List<Role> roles = roleMapper.findRoleByUserIds(new int[]{userId});
            buildRoleSetPermissions(roles);
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int insertUser(User user) {
        userMapper.insertUser(user);
        if(user.getRoles()!=null&&!user.getRoles().isEmpty()){
            userMapper.insertUserRole(user.getUserId(),user.getRoles().stream().mapToInt(Role::getRoleId).toArray());
        }
        return user.getUserId();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int deleteUser(int userId) {
        userMapper.deleteUser(userId);
        userMapper.deleteUserRoleByUserId(userId);
        return 0;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int updateUser(User user) {
        userMapper.updateUser(user);
        userMapper.deleteUserRoleByUserId(user.getUserId());
        if(user.getRoles()!=null&&!user.getRoles().isEmpty())
            userMapper.insertUserRole(user.getUserId(),user.getRoles().stream().mapToInt(Role::getRoleId).toArray());
        return 0;
    }

    @Override
    public List<Role> getAllRole(int pageNo, int limit) {
        List<Role> roles = roleMapper.findAllRole(pageNo*limit,limit);
        buildRoleSetPermissions(roles);
        return roles;
    }

    @Override
    public Role getRoleInfo(int roleId) {
        Role role = roleMapper.findRoleByRoleId(roleId);
        List<Permission> permissions = permissionMapper.findPermissionByRoleIds(new int[]{roleId});
        role.setPermissions(permissions);
        return role;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int insertRole(Role role) {
        roleMapper.insertRole(role);
        if(role.getPermissions()!=null&&!role.getPermissions().isEmpty())
            roleMapper.insertRolePermissions(role.getRoleId(),role.getPermissions().stream().mapToInt(Permission::getPermissionId).toArray());
        return role.getRoleId();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int deleteRole(int roleId) {
        int count = userMapper.findUserCountByRoleId(roleId);
        if(count == 0){
            roleMapper.deleteRole(roleId);
            roleMapper.deleteRolePermissions(roleId);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int updateRole(Role role) {
        roleMapper.updateRole(role);
        roleMapper.deleteRolePermissions(role.getRoleId());
        if(role.getPermissions()!=null&&!role.getPermissions().isEmpty())
            roleMapper.insertRolePermissions(role.getRoleId(),role.getPermissions().stream().mapToInt(Permission::getPermissionId).toArray());
        return 0;
    }

    @Override
    public List<Permission> getAllPermission(int pageNo, int limit) {
        return permissionMapper.findAllPermission(pageNo*limit,limit);
    }

    @Override
    public int insertPermission(Permission permission) {
        permissionMapper.insertPermission(permission);
        return permission.getPermissionId();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int deletePermission(int permissionId) {
        return permissionMapper.deletePermission(permissionId);
    }

    @Override
    @Transactional(rollbackFor= Exception.class)
    public int updatePermission(Permission permission) {
        return permissionMapper.updatePermission(permission);
    }

    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        if(user!=null){
            List<Role> roles = roleMapper.findRoleByUserIds(new int[]{user.getUserId()});
            buildRoleSetPermissions(roles);
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public boolean canNotDeletePermission(int permissionId) {
        return permissionMapper.countRolePermission(permissionId)>0;
    }

    @Override
    public boolean canNotDeleteRole(int roleId) {
        return roleMapper.countUserRole(roleId)>0;
    }

    @Override
    public int getPermissionCount() {
        return permissionMapper.getPermissionCount();
    }

    @Override
    public int getRoleCount() {
        return roleMapper.getRoleCount();
    }

    private void buildRoleSetPermissions(List<Role> roles){
        if(roles!=null&&!roles.isEmpty()){
            List<Permission> permissions = permissionMapper.findPermissionByRoleIds(roles.stream().mapToInt(Role::getRoleId).toArray());
            if(permissions!=null&&!permissions.isEmpty()){
                Map<Integer,List<Permission>> map = new HashMap<>();
                permissions.forEach(permission -> {
                    map.computeIfAbsent(permission.getRoleId(), k -> new ArrayList<>());
                    map.get(permission.getRoleId()).add(permission);
                });
                roles.forEach(role -> role.setPermissions(map.get(role.getRoleId())));
            }
        }
    }
}
