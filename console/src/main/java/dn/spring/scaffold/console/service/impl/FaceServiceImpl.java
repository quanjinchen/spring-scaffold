package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CompareFaceFeatureReqParam;
import dn.spring.scaffold.console.pojo.req.ExtractFaceFeatureReqParam;
import dn.spring.scaffold.console.pojo.req.OpenFaceCompareReqParam;
import dn.spring.scaffold.console.pojo.req.OpenFaceSearchReqParam;
import dn.spring.scaffold.console.pojo.resp.FaceCompareDTO;
import dn.spring.scaffold.console.pojo.resp.FaceFeatureDTO;
import dn.spring.scaffold.console.pojo.resp.OpenFaceCompareDTO;
import dn.spring.scaffold.console.pojo.resp.OpenFaceSearchDTO;
import dn.spring.scaffold.console.pojo.resp.OpenFaceSearchUserDTO;
import dn.spring.scaffold.console.service.FaceService;
import dn.spring.scaffold.console.service.face.FaceFeatureEngine;
import dn.spring.scaffold.framework.auth.AccessTokenManager;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.UserManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;
import java.util.List;

@Service
public class FaceServiceImpl implements FaceService {

    @Resource
    private FaceFeatureEngine faceFeatureEngine;
    @Resource
    private AccessTokenManager accessTokenManager;
    @Resource
    private UserManager userManager;

    private static final float FACE_MATCH_THRESHOLD = 0.75F;

    @Override
    public RespInfo<FaceFeatureDTO> extractFaceFeature(ExtractFaceFeatureReqParam reqParam) {
        FaceFeatureDTO faceFeatureDTO = new FaceFeatureDTO();
        faceFeatureDTO.setFeature(faceFeatureEngine.extractFeatureBase64(reqParam.getImageBase64()));
        return RespInfo.success(faceFeatureDTO);
    }

    @Override
    public RespInfo<FaceCompareDTO> compareFaceFeature(CompareFaceFeatureReqParam reqParam) {
        FaceCompareDTO faceCompareDTO = new FaceCompareDTO();
        faceCompareDTO.setSimilarity(faceFeatureEngine.compare(reqParam.getSourceImageBase64(), reqParam.getTargetImageBase64()));
        return RespInfo.success(faceCompareDTO);
    }

    @Override
    public RespInfo<OpenFaceCompareDTO> openCompareFace(OpenFaceCompareReqParam reqParam) {
        checkAccessToken(reqParam.getAccessToken());

        User user = userManager.getByIdCard(reqParam.getIdCard());
        ResultCode.USER_NOT_FOUND.assertNotNull(user);
        ResultCode.FACE_FEATURE_NOT_FOUND.assertIsTrue(StringUtils.hasText(user.getFaceFeature()));

        float[] inputFeatures = faceFeatureEngine.extractFeatureArray(reqParam.getFaceImageBase64());
        float[] storedFeatures = decodeFeature(reqParam.getIdCard(), user.getFaceFeature());
        float score = faceFeatureEngine.compare(inputFeatures, storedFeatures);

        OpenFaceCompareDTO result = new OpenFaceCompareDTO();
        result.setMatched(score >= FACE_MATCH_THRESHOLD);
        result.setScore(score);
        return RespInfo.success(result);
    }

    @Override
    public RespInfo<OpenFaceSearchDTO> openSearchFace(OpenFaceSearchReqParam reqParam) {
        checkAccessToken(reqParam.getAccessToken());

        List<User> users = userManager.listUsersWithFaceFeature();
        ResultCode.FACE_FEATURE_NOT_FOUND.assertIsFalse(users.isEmpty());

        float[] inputFeatures = faceFeatureEngine.extractFeatureArray(reqParam.getFaceImageBase64());
        User bestUser = null;
        float bestScore = -1F;

        for (User user : users) {
            if (!StringUtils.hasText(user.getFaceFeature())) {
                continue;
            }
            float[] storedFeatures = decodeFeature(String.valueOf(user.getId()), user.getFaceFeature());
            float score = faceFeatureEngine.compare(inputFeatures, storedFeatures);
            if (score > bestScore) {
                bestScore = score;
                bestUser = user;
            }
        }

        OpenFaceSearchDTO result = new OpenFaceSearchDTO();
        result.setMatched(bestUser != null && bestScore >= FACE_MATCH_THRESHOLD);
        result.setScore(bestUser == null ? 0F : bestScore);
        if (bestUser != null && bestScore >= FACE_MATCH_THRESHOLD) {
            OpenFaceSearchUserDTO userInfo = new OpenFaceSearchUserDTO();
            userInfo.setIdCard(bestUser.getIdCard() == null ? null : bestUser.getIdCard().getPlainText());
            userInfo.setPhoneNum(bestUser.getPhone() == null ? null : bestUser.getPhone().getPlainText());
            userInfo.setFullName(bestUser.getFullName());
            userInfo.setUserName(bestUser.getUsername());
            result.setUserInfo(userInfo);
        }
        return RespInfo.success(result);
    }

    private void checkAccessToken(String accessToken) {
        ResultCode.FACE_ACCESS_TOKEN_INVALID.assertIsTrue(accessTokenManager.checkAccessToken(accessToken));
    }

    private float[] decodeFeature(String bizId, String featureBase64) {
        try {
            byte[] bytes = Base64.getDecoder().decode(featureBase64);
            float[] result = new float[bytes.length / 4];
            ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().get(result);
            return result;
        } catch (Exception exception) {
            throw ResultCode.FACE_FEATURE_EXTRACT_FAILED.newException("用户特征值解析失败: " + bizId);
        }
    }
}
