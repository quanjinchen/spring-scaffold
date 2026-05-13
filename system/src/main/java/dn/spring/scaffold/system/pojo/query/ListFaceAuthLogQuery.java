package dn.spring.scaffold.system.pojo.query;

import dn.spring.scaffold.system.enums.FaceAuthApiTypeEnum;
import lombok.Data;

@Data
public class ListFaceAuthLogQuery {

    private FaceAuthApiTypeEnum authApiType;

    private String ip;

    private Integer status;

    private String appName;

    private String authFullName;
}
