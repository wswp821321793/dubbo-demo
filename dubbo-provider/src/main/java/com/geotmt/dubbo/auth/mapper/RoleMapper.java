package com.geotmt.dubbo.auth.mapper;

import com.geotmt.dubbo.auth.po.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {

    @Results(id="user_role",value={
            @Result(property="roleId",column="id"),
            @Result(property="roleName",column="role_name"),
            @Result(property="userId",column="user_id"),
            @Result(property="remark",column="remark")
    })
    @Select("<script>select r.*,ur.user_id from role r right join user_role ur on id = role_id where user_id in <foreach collection='userIds' open='(' item='userId' separator=',' close=')'>#{userId}</foreach></script>")
    List<Role> findRoleByUserIds(@Param("userIds")int[] userIds);

    @Results(id="role",value={
            @Result(property="roleId",column="id"),
            @Result(property="roleName",column="role_name"),
            @Result(property="remark",column="remark")
    })
    @Select("select * from role order by id limit #{start},#{limit}")
    List<Role> findAllRole(@Param("start")int start,@Param("limit")int limit);

    @Options(useGeneratedKeys=true, keyProperty="roleId", keyColumn="id")
    @Insert("insert into role (role_name,remark) values (#{roleName},#{remark})")
    void insertRole(Role role);

    @Delete("delete from role where id = #{roleId}")
    int deleteRole(int roleId);

    @Update("update role set role_name = #{roleName},remark = #{remark} where id= #{roleId}")
    int updateRole(Role role);

    @Delete("delete from role_permission where role_id = #{roleId}")
    int deleteRolePermissions(int roleId);

    @Insert("<script> INSERT INTO role_permission (role_id,permission_id) VALUES <foreach collection='permissionIds' item='permissionId' separator=','> (#{roleId},#{permissionId})</foreach></script>")
    int insertRolePermissions(@Param("roleId")int roleId,@Param("permissionIds")int[] permissionIds);

    @ResultMap("role")
    @Select("select * from role where id = #{roleId}")
    Role findRoleByRoleId(int roleId);

    @Select("select count(*) from role_permission where role_id = #{roleId}")
    int countUserRole(int roleId);

    @Select("select count(*) from role")
    int getRoleCount();
}
