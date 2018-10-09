package com.geotmt.dubbo.auth.request;

import com.geotmt.dubbo.auth.common.Request;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class RoleRequest extends Request implements Serializable {

    private int roleId;

    private String roleName;

    private String remark;

    private List<PermissionRequest> permissions;
}
