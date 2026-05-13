package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CompareFaceFeatureReqParam;
import dn.spring.scaffold.console.pojo.req.ExtractFaceFeatureReqParam;
import dn.spring.scaffold.console.pojo.req.OpenFaceCompareReqParam;
import dn.spring.scaffold.console.pojo.req.OpenFaceSearchReqParam;
import dn.spring.scaffold.console.pojo.resp.FaceCompareDTO;
import dn.spring.scaffold.console.pojo.resp.FaceFeatureDTO;
import dn.spring.scaffold.console.pojo.resp.OpenFaceCompareDTO;
import dn.spring.scaffold.console.pojo.resp.OpenFaceSearchDTO;

public interface FaceService {

    RespInfo<FaceFeatureDTO> extractFaceFeature(ExtractFaceFeatureReqParam reqParam);

    RespInfo<FaceCompareDTO> compareFaceFeature(CompareFaceFeatureReqParam reqParam);

    RespInfo<OpenFaceCompareDTO> openCompareFace(OpenFaceCompareReqParam reqParam);

    RespInfo<OpenFaceSearchDTO> openSearchFace(OpenFaceSearchReqParam reqParam);
}
