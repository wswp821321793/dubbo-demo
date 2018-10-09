package com.geotmt.dubbo.auth.mapper;

import com.geotmt.dubbo.auth.po.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Results(id="user",value={
            @Result(property="userId",column="id"),
            @Result(property="username",column="username"),
            @Result(property="password",column="password"),
            @Result(property="name",column="name")

    })
    @Select("select * from user where userName = #{userName}")
    User findByUserName(String userName);

    @Insert("insert into user (username,password,name) values (#{username},#{password},#{name})")
    @Options(useGeneratedKeys=true, keyProperty="userId", keyColumn="id")
    void insertUser(User user);

    @Delete("delete from user where id = #{userId}")
    int deleteUser(int userId);

    @Update("update user set name=#{name},username=#{username},password=#{password} where id = #{userId}")
    int updateUser(User user);

    @ResultMap("user")
    @Select("select * from user where id = #{userId}")
    User findByUserId(int userId);

    @Insert("<script> INSERT INTO user_role (user_id,role_id) VALUES  <foreach collection='roleIds' item='roleId' separator=','> (#{userId},#{roleId})</foreach></script>")
    int insertUserRole(@Param("userId")int userId, @Param("roleIds")int[] roleIds);

    @Delete("delete from user_role where user_id = #{userId}")
    int deleteUserRoleByUserId(int userId);

    @ResultMap("user")
    @Select("select * from user order by id limit #{start},#{limit}")
    List<User> findAllUser(@Param("start")int start,@Param("limit")int limit);

    @Select("select count(*) from user_role where role_id = #{roleId}")
    int findUserCountByRoleId(int roleId);

    @Select("select count(*) from user")
    int getUserCount();
}
