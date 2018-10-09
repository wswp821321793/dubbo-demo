package com.geotmt.dubbo.auth.mapper;

import com.geotmt.dubbo.auth.po.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PermissionMapper {

    @Results(id="role_permission",value={
            @Result(property="permissionId",column="id"),
            @Result(property="permissionName",column="permission_name"),
            @Result(property="data",column="data"),
            @Result(property="roleId",column="role_id"),
            @Result(property="parentId",column="parent_id"),
            @Result(property="source",column="source")
    })
    @Select("<script>select p.*,rp.role_id from permission p right join role_permission rp on id = permission_id where role_id in <foreach collection='roleIds' open='(' item='roleId' separator=',' close=')'>#{roleId}</foreach></script>")
    List<Permission> findPermissionByRoleIds(@Param("roleIds") int[] roleIds);

    @Results(id="permission",value={
            @Result(property="permissionId",column="id"),
            @Result(property="permissionName",column="permission_name"),
            @Result(property="data",column="data"),
            @Result(property="parentId",column="parent_id"),
            @Result(property="source",column="source")
    })
    @Select("select * from permission order by id limit #{start},#{limit}")
    List<Permission> findAllPermission(@Param("start")int start,@Param("limit")int limit);

    @Insert("insert into permission (permission_name,data,parent_id,source) values (#{permissionName},#{data},#{parentId},#{source})")
    @Options(useGeneratedKeys=true, keyProperty="permissionId", keyColumn="id")
    void insertPermission(Permission permission);

    @Delete("delete from permission where id = #{permissionId}")
    int deletePermission(int permissionId);

    @Update("update permission set permission_name = #{permissionName},data = #{data},source = #{source} where id = #{permissionId}")
    int updatePermission(Permission permission);

    @Select("select count(*) from role_permission where permission_id = #{permissionId}")
    int countRolePermission(int permissionId);

    @Select("select count(*) from permission")
    int getPermissionCount();
}
