package com.geotmt.dubbo.auth.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleResponse implements Serializable {
    private int roleId;

    private String roleName;

    private String remark;

    private List<PermissionResponse> permissions;
}
