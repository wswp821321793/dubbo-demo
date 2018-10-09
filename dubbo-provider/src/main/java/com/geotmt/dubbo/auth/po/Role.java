package com.geotmt.dubbo.auth.po;

import lombok.Data;

import java.util.List;

@Data
public class Role {

    private int userId;

    private int roleId;

    private String roleName;

    private String remark;

    private List<Permission> permissions;
}
