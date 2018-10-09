package com.geotmt.dubbo.auth.po;

import lombok.Data;

@Data
public class Permission {

    private int roleId;

    private int permissionId;

    private String permissionName;

    private String data;

    private String source;

    private int parentId;

}
