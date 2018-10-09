package com.geotmt.dubbo.auth.request;

import com.geotmt.dubbo.auth.common.Request;
import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionRequest extends Request implements Serializable {

    private int permissionId;

    private String permissionName;

    private String data;

    private String source;

    private int parentId;
}
