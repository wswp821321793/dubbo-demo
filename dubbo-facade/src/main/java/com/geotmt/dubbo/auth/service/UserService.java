package com.geotmt.dubbo.auth.service;

import com.geotmt.dubbo.auth.request.UserRequest;
import com.geotmt.dubbo.auth.common.Response;
import com.geotmt.dubbo.auth.request.PermissionRequest;
import com.geotmt.dubbo.auth.request.RoleRequest;
import com.geotmt.dubbo.auth.response.*;

public interface UserService {

    /**
     * 权限增
     * @param request
     * @return
     */
    Response<Integer> insertPermission(PermissionRequest request);

    /**
     * 权限删
     * @param permissionId
     * @return
     */
    Response<Integer> deletePermission(int permissionId);

    /**
     * 权限改
     * @param request
     * @return
     */
    Response<Integer> updatePermission(PermissionRequest request);

    /**
     * 权限查
     * @return
     */
    PageGetPermissionResponse getAllPermission(int pageNo, int limit);

    /**
     * 角色增
     * @param request
     * @return
     */
    Response<Integer> insertRole(RoleRequest request);

    /**
     * 角色删
     * @param roleId
     * @return
     */
    Response<Integer> deleteRole(int roleId);

    /**
     * 角色改
     * @param request
     * @return
     */
    Response<Integer> updateRole(RoleRequest request);

    /**
     * 角色详情
     * @param roleId
     * @return
     */
    Response<RoleResponse> getRoleInfo(int roleId);

    /**
     * 全部角色
     * @return
     */
    PageGetRoleResponse getAllRole(int pageNo, int limit);

    /**
     * 用户增
     * @param request
     * @return
     */
    Response<Integer> insertUser(UserRequest request);

    /**
     * 用户删
     * @param userId
     * @return
     */
    Response<Integer> deleteUser(int userId);

    /**
     * 用户改
     * @param request
     * @return
     */
    Response<Integer> updateUser(UserRequest request);

    /**
     * 获取用户详情
     * @param userId
     * @return
     */
    Response<UserResponse> getUserInfo(int userId);

    /**
     * 分页获取所有用户
     * @param pageNo
     * @param limit
     * @return
     */
    PageGetUserResponse getAllUser(int pageNo, int limit);

    /**
     * 根据用户名获取用户详情
     * @param username
     * @return
     */
    UserResponse findByUserName(String username);
}
