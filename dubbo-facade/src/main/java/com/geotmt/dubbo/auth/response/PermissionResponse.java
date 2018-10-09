package com.geotmt.dubbo.auth.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermissionResponse implements Serializable {
    private int permissionId;

    private String permissionName;

    private String data;

    private int parentId;

    private String source;

    private List<PermissionResponse> children;
}
