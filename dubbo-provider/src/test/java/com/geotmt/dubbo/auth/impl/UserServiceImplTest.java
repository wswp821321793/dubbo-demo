package com.geotmt.dubbo.auth.impl;

import com.geotmt.dubbo.auth.bo.UserBo;
import com.geotmt.dubbo.auth.po.Permission;
import com.geotmt.dubbo.auth.po.Role;
import com.geotmt.dubbo.auth.po.User;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
@Log4j2
public class UserServiceImplTest {

    @Autowired
    UserBo userBo;

    @Test
    public void insertPermission(){
        log.info("------【测试开始】插入权限-----");
        Permission permission = new Permission();
        permission.setPermissionName("缓存管理");
        permission.setData("web/o/redis");
        userBo.insertPermission(permission);
        permission.setPermissionName("报告管理");
        permission.setData("web/o/report");
        userBo.insertPermission(permission);
        permission.setPermissionName("日志管理");
        permission.setData("web/o/log");
        userBo.insertPermission(permission);
        permission.setPermissionName("统计管理");
        permission.setData("web/o/sum");
        userBo.insertPermission(permission);
        log.info("------【测试结束】插入权限-----");
    }

    @Test
    public void getAllPermission(){
        log.info("------【测试开始】获取全部权限-----");
        List<Permission> permissions = userBo.getAllPermission(1,10);
        log.info("获取全部权限：{}",permissions.toArray());
        log.info("------【测试结束】获取全部权限-----");
    }

    @Test
    public void insertRole(){
        log.info("------【测试开始】插入角色-----");
        Role role = new Role();
        role.setRoleName("企业管理员");
        role.setPermissions(userBo.getAllPermission(1,10));
        int roleId = userBo.insertRole(role);
        log.info("插入角色：{}",roleId);
        log.info("------【测试结束】插入角色-----");
    }

    @Test
    public void getAllRole(){
        log.info("------【测试开始】获取角色-----");
        List<Role> roles = userBo.getAllRole(1,10);
        log.info("获取全部角色：{}",roles.toArray());
        log.info("------【测试结束】获取角色-----");
    }

    @Test
    public void deleteRole(){
        log.info("------【测试开始】删除角色-----");
        userBo.deleteRole(16);
        log.info("------【测试结束】删除角色-----");
    }

    @Test
    public void getRoleInfo(){
        log.info("------【测试开始】获取角色详情-----");
        Role role = userBo.getRoleInfo(17);
        log.info("角色详情：{}",role);
        log.info("------【测试结束】获取角色详情-----");
    }

    @Test
    public void insertUser(){
        log.info("------【测试开始】插入用户-----");
        for(int i=0;i<100;i++){
            User user = new User();
            user.setRoles(userBo.getAllRole(1,10));
            user.setName("测试"+i);
            user.setPassword("123456");
            user.setUsername("ceshi"+i);
            userBo.insertUser(user);
            log.info("插入用户id：{}",user.getUserId());
        }
        log.info("------【测试结束】插入用户-----");
    }

    @Test
    public void getUserInfo(){
        log.info("------【测试开始】获取用户详情-----");
        User user = userBo.getUserInfo(2);
        log.info("用户详情：{}",user.toString());
        log.info("------【测试结束】获取用户详情-----");
    }

    @Test
    public void getAllUser(){
        log.info("------【测试开始】分页获取用户列表-----");
        List<User> users = userBo.getAllUser(0,10);
        log.info("用户详情：{}",users.toString());
        log.info("------【测试结束】分页获取用户列表-----");
    }

    @Test
    public void updateUser(){
        log.info("------【测试开始】修改用户-----");
        User user = new User();
        user.setUserId(2);
        user.setName("去掉角色");
        user.setPassword("123456");
        user.setUsername("hahah");
        userBo.updateUser(user);
        log.info("用户详情：{}",user);
        log.info("------【测试结束】修改用户-----");
    }

    @Test
    public void deleteUser(){
        log.info("------【测试开始】删除角色-----");
        userBo.deleteUser(3);
        log.info("------【测试结束】删除角色-----");
    }

}