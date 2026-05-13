package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.system.entity.FaceAuthLog;
import dn.spring.scaffold.system.pojo.query.ListFaceAuthLogQuery;

import java.util.List;

public interface FaceAuthLogManager {

    void save(FaceAuthLog faceAuthLog);

    List<FaceAuthLog> listFaceAuthLogs(ListFaceAuthLogQuery query);
}
