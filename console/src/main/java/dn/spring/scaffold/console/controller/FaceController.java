package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CompareFaceFeatureReqParam;
import dn.spring.scaffold.console.pojo.req.ExtractFaceFeatureReqParam;
import dn.spring.scaffold.console.pojo.req.OpenFaceCompareReqParam;
import dn.spring.scaffold.console.pojo.req.OpenFaceSearchReqParam;
import dn.spring.scaffold.console.pojo.resp.FaceCompareDTO;
import dn.spring.scaffold.console.pojo.resp.FaceFeatureDTO;
import dn.spring.scaffold.console.pojo.resp.OpenFaceCompareDTO;
import dn.spring.scaffold.console.pojo.resp.OpenFaceSearchDTO;
import dn.spring.scaffold.console.service.FaceService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "人脸识别")
@RestController
@RequestMapping("/face")
public class FaceController {

    @Resource
    private FaceService faceService;

    @Operation(summary = "提取人脸特征值", description = "权限：system:user:query")
    @PostMapping("/extract-face-feature")
    public RespInfo<FaceFeatureDTO> extractFaceFeature(@Valid @RequestBody ExtractFaceFeatureReqParam reqParam) {
        return faceService.extractFaceFeature(reqParam);
    }

    @Operation(summary = "人脸特征值比对", description = "权限：system:user:query")
    @PostMapping("/compare-face-feature")
    @OperateLog(module = "face", action = "人脸特征值比对")
    public RespInfo<FaceCompareDTO> compareFaceFeature(@Valid @RequestBody CompareFaceFeatureReqParam reqParam) {
        return faceService.compareFaceFeature(reqParam);
    }

    @Operation(summary = "开放 1 比 1 人脸比对")
    @PostMapping("/compare")
    public RespInfo<OpenFaceCompareDTO> compare(@Valid @RequestBody OpenFaceCompareReqParam reqParam) {
        return faceService.openCompareFace(reqParam);
    }

    @Operation(summary = "开放 1 比 N 人脸检索")
    @PostMapping("/search")
    public RespInfo<OpenFaceSearchDTO> search(@Valid @RequestBody OpenFaceSearchReqParam reqParam) {
        return faceService.openSearchFace(reqParam);
    }
}
