package com.geotmt.dubbo.auth.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageGetRoleResponse  implements Serializable {
    private int code;

    private int count;

    private List<RoleResponse> data;
}
