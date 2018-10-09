package com.geotmt.dubbo.auth.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.geotmt.dubbo.auth.request.UserRequest;
import com.geotmt.dubbo.auth.response.*;
import com.geotmt.dubbo.auth.service.UserService;
import com.geotmt.dubbo.auth.bo.UserBo;
import com.geotmt.dubbo.auth.code.ErrorCode;
import com.geotmt.dubbo.auth.common.Response;
import com.geotmt.dubbo.auth.po.Permission;
import com.geotmt.dubbo.auth.po.Role;
import com.geotmt.dubbo.auth.po.User;
import com.geotmt.dubbo.auth.request.PermissionRequest;
import com.geotmt.dubbo.auth.request.RoleRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserBo userBo;

    @Override
    public Response<Integer> insertPermission(PermissionRequest request) {
        Response<Integer> response = new Response<>();
        try {
            Permission permission = new Permission();
            permission.setPermissionName(request.getPermissionName());
            permission.setData(request.getData());
            permission.setSource(request.getSource());
            permission.setParentId(request.getParentId());
            response.setData(userBo.insertPermission(permission));
            response.setCode(200);
        }catch (Exception e){
            log.error("【插入权限】出现异常",e);
            response.setCode(ErrorCode.USER_UNKNOWN_ERROR.getCode());
            response.setMsg(ErrorCode.USER_UNKNOWN_ERROR.getMsg());
        }
        return response;
    }

    @Override
    public Response<Integer> deletePermission(int permissionId) {
        Response<Integer> response = new Response<>();
        try {
            if(userBo.canNotDeletePermission(permissionId)){
                response.setCode(ErrorCode.ROLE_HAD_PERMISSION.getCode());
                response.setMsg(ErrorCode.ROLE_HAD_PERMISSION.getMsg());
            }else{
                response.setData(userBo.deletePermission(permissionId));
                response.setCode(200);
            }
        }catch (Exception e){
            log.error("【删除权限】出现异常",e);
            response.setCode(ErrorCode.USER_UNKNOWN_ERROR.getCode());
            response.setMsg(ErrorCode.USER_UNKNOWN_ERROR.getMsg());
        }
        return response;
    }

    @Override
    public Response<Integer> updatePermission(PermissionRequest request) {
        Response<Integer> response = new Response<>();
        try {
            Permission permission = new Permission();
            permission.setPermissionId(request.getPermissionId());
            permission.setPermissionName(request.getPermissionName());
            permission.setData(request.getData());
            permission.setSource(request.getSource());
            permission.setParentId(request.getParentId());
            response.setData(userBo.updatePermission(permission));
            response.setCode(200);
        }catch (Exception e){
            log.error("【修改权限】出现异常",e);
            response.setCode(10000);
            response.setMsg("【未知错误】请联系开发人员");
        }
        return response;
    }

    @Override
    public PageGetPermissionResponse getAllPermission(int pageNo, int limit) {
        PageGetPermissionResponse response = new PageGetPermissionResponse();
        try{
            response.setCode(0);
            response.setCount(userBo.getPermissionCount());
            if(response.getCount()!=0)
                response.setData(userBo.getAllPermission(pageNo,limit).stream().map(permission->{
                PermissionResponse permissionResponse = new PermissionResponse();
                permissionResponse.setPermissionId(permission.getPermissionId());
                permissionResponse.setPermissionName(permission.getPermissionName());
                permissionResponse.setParentId(permission.getParentId());
                permissionResponse.setData(permission.getData());
                permissionResponse.setSource(permission.getSource());
                return permissionResponse;
            }).collect(Collectors.toList()));
        }catch (Exception e){
            log.error("【获取全部用户】出现异常",e);
        }
        return response;
    }

    private Role buildRoleByRequest(RoleRequest request){
        Role role = new Role();
        role.setRoleId(request.getRoleId());
        role.setRoleName(request.getRoleName());
        role.setRemark(request.getRemark());
        if(request.getPermissions()!=null&&!request.getPermissions().isEmpty()){
            List<Permission> permissions = new ArrayList<>(request.getPermissions().size());
            request.getPermissions().forEach(permissionRequest -> {
                Permission permission = new Permission();
                permission.setPermissionId(permissionRequest.getPermissionId());
                permission.setPermissionName(permissionRequest.getPermissionName());
                permission.setData(permissionRequest.getData());
                permission.setSource(permissionRequest.getSource());
                permission.setParentId(permissionRequest.getParentId());
                permissions.add(permission);
            });
            role.setPermissions(permissions);
        }
        return role;
    }

    @Override
    public Response<Integer> insertRole(RoleRequest request) {
        Response<Integer> response = new Response<>();
        try {
            Role role = buildRoleByRequest(request);
            response.setData(userBo.insertRole(role));
            response.setCode(200);
        }catch (Exception e){
            log.error("【插入角色】出现异常",e);
            response.setCode(10000);
            response.setMsg("【未知错误】请联系开发人员");
        }
        return response;
    }

    @Override
    public Response<Integer> deleteRole(int roleId) {
        Response<Integer> response = new Response<>();
        try {
            if(userBo.canNotDeleteRole(roleId)){
                response.setCode(ErrorCode.USER_HAD_ROLE.getCode());
                response.setMsg(ErrorCode.USER_HAD_ROLE.getMsg());
            }else {
                response.setData(userBo.deleteRole(roleId));
                response.setCode(200);
            }
        }catch (Exception e){
            log.error("【删除角色】出现异常",e);
            response.setCode(10000);
            response.setMsg("【未知错误】请联系开发人员");
        }
        return response;
    }

    @Override
    public Response<Integer> updateRole(RoleRequest request) {
        Response<Integer> response = new Response<>();
        try {
            Role role = buildRoleByRequest(request);
            response.setData(userBo.updateRole(role));
            response.setCode(200);
        }catch (Exception e){
            log.error("【修改角色】出现异常",e);
            response.setCode(10000);
            response.setMsg("【未知错误】请联系开发人员");
        }
        return response;
    }

    private RoleResponse buildRoleResponseByRole(Role request){
        RoleResponse role = new RoleResponse();
        role.setRoleId(request.getRoleId());
        role.setRoleName(request.getRoleName());
        role.setRemark(request.getRemark());
        if(request.getPermissions()!=null&&!request.getPermissions().isEmpty()){
            List<PermissionResponse> permissions = new ArrayList<>(request.getPermissions().size());
            request.getPermissions().forEach(permissionRequest -> {
                PermissionResponse permission = new PermissionResponse();
                permission.setPermissionId(permissionRequest.getPermissionId());
                permission.setPermissionName(permissionRequest.getPermissionName());
                permission.setData(permissionRequest.getData());
                permission.setSource(permissionRequest.getSource());
                permission.setParentId(permissionRequest.getParentId());
                permissions.add(permission);
            });
            role.setPermissions(permissions);
        }
        return role;
    }

    @Override
    public Response<RoleResponse> getRoleInfo(int roleId) {
        Response<RoleResponse> response = new Response<>();
        try{
            response.setData(buildRoleResponseByRole(userBo.getRoleInfo(roleId)));
            response.setCode(200);
        }catch (Exception e){
            log.error("【获取角色】出现异常",e);
            response.setCode(10000);
            response.setMsg("【未知错误】请联系开发人员");
        }
        return response;
    }

    @Override
    public PageGetRoleResponse getAllRole(int pageNo, int limit) {
        PageGetRoleResponse response = new PageGetRoleResponse();
        try{
            response.setCode(0);
            response.setCount(userBo.getRoleCount());
            if(response.getCount()!=0)
                response.setData(userBo.getAllRole(pageNo,limit).stream().map(this::buildRoleResponseByRole).collect(Collectors.toList()));
        }catch (Exception e){
            log.error("【获取全部用户】出现异常",e);
        }
        return response;
    }

    private User buildUserByUserRequest(UserRequest request){
        User user =  new User();
        user.setUserId(request.getUserId());
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(DigestUtils.md5Hex(request.getPassword()));
        List<Role> roles = new ArrayList<>();
        request.getRoles().forEach(roleRequest->{
            Role role = new Role();
            role.setRoleId(roleRequest.getRoleId());
            role.setRoleName(roleRequest.getRoleName());
            roles.add(role);
        });
        user.setRoles(roles);
        return user;
    }

    @Override
    public Response<Integer> insertUser(UserRequest request) {
        Response<Integer> response = new Response<>();
        try{
            response.setData(userBo.insertUser(buildUserByUserRequest(request)));
            response.setCode(200);
        }catch (Exception e){
            log.error("【新增用户】出现异常",e);
            response.setCode(10000);
            response.setMsg("【未知错误】请联系开发人员");
        }
        return response;
    }

    @Override
    public Response<Integer> deleteUser(int userId) {
        Response<Integer> response = new Response<>();
        try{
            response.setData(userBo.deleteUser(userId));
            response.setCode(200);
        }catch (Exception e){
            log.error("【删除用户】出现异常",e);
            response.setCode(10000);
            response.setMsg("【未知错误】请联系开发人员");
        }
        return response;
    }

    @Override
    public Response<Integer> updateUser(UserRequest request) {
        Response<Integer> response = new Response<>();
        try{
            response.setData(userBo.updateUser(buildUserByUserRequest(request)));
            response.setCode(200);
        }catch (Exception e){
            log.error("【修改用户】出现异常",e);
            response.setCode(10000);
            response.setMsg("【未知错误】请联系开发人员");
        }
        return response;
    }

    private UserResponse buildUserResponseByUser(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getUserId());
        userResponse.setName(user.getName());
        userResponse.setPassword(user.getPassword());
        userResponse.setUsername(user.getUsername());
        if(user.getRoles()!=null)
            userResponse.setRoles(user.getRoles().stream().map(this::buildRoleResponseByRole).collect(Collectors.toList()));
        return userResponse;
    }

    @Override
    public Response<UserResponse> getUserInfo(int userId) {
        Response<UserResponse> response = new Response<>();
        try{
            response.setData(buildUserResponseByUser(userBo.getUserInfo(userId)));
            response.setCode(200);
        }catch (Exception e){
            log.error("【用户详情】出现异常",e);
            response.setCode(10000);
            response.setMsg("【未知错误】请联系开发人员");
        }
        return response;
    }

    @Override
    public PageGetUserResponse getAllUser(int pageNo,int limit) {
        PageGetUserResponse pageGetUserResponse = new PageGetUserResponse();
        try{
            pageGetUserResponse.setCode(0);
            pageGetUserResponse.setCount(userBo.getUserCount());
            if(pageGetUserResponse.getCount()!=0)
                pageGetUserResponse.setData(userBo.getAllUser(pageNo,limit).stream().map(this::buildUserResponseByUser).collect(Collectors.toList()));
        }catch (Exception e){
            log.error("【获取全部用户】出现异常",e);
        }
        return pageGetUserResponse;
    }

    @Override
    public UserResponse findByUserName(String username) {
        return buildUserResponseByUser(userBo.findByUserName(username));
    }


}
