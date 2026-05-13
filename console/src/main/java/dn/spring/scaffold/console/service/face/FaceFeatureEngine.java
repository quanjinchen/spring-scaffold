package dn.spring.scaffold.console.service.face;

import cn.hutool.core.codec.Base64;
import com.seeta.sdk.FaceDetector;
import com.seeta.sdk.FaceLandmarker;
import com.seeta.sdk.FaceRecognizer;
import com.seeta.sdk.SeetaDevice;
import com.seeta.sdk.SeetaImageData;
import com.seeta.sdk.SeetaModelSetting;
import com.seeta.sdk.SeetaPointF;
import com.seeta.sdk.SeetaRect;
import com.seeta.sdk.util.LoadNativeCore;
import com.seeta.sdk.util.SeetafaceUtil;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.exception.BizException;
import dn.spring.scaffold.console.config.FaceEngineProperties;
import dn.spring.scaffold.file.util.DataUrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class FaceFeatureEngine {

    private final FaceEngineProperties faceEngineProperties;

    private FaceDetector faceDetector;

    private FaceLandmarker faceLandmarker;

    private FaceRecognizer faceRecognizer;

    public FaceFeatureEngine(FaceEngineProperties faceEngineProperties) {
        this.faceEngineProperties = faceEngineProperties;
    }

    @PostConstruct
    public void init() {
        try {
            LoadNativeCore.LOAD_NATIVE(SeetaDevice.SEETA_DEVICE_AUTO);

            String detectorModelPath = prepareModelPath(faceEngineProperties.getDetectorModel());
            String landmarkerModelPath = prepareModelPath(faceEngineProperties.getLandmarkerModel());
            String recognizerModelPath = prepareModelPath(faceEngineProperties.getRecognizerModel());

            faceDetector = new FaceDetector(new SeetaModelSetting(new String[]{detectorModelPath}, SeetaDevice.SEETA_DEVICE_AUTO));
            faceLandmarker = new FaceLandmarker(new SeetaModelSetting(new String[]{landmarkerModelPath}, SeetaDevice.SEETA_DEVICE_AUTO));
            faceRecognizer = new FaceRecognizer(new SeetaModelSetting(new String[]{recognizerModelPath}, SeetaDevice.SEETA_DEVICE_AUTO));
        } catch (Exception exception) {
            throw ResultCode.FACE_ENGINE_INIT_FAILED.newException(exception, exception.getMessage());
        }
    }

    public String extractFeatureBase64(String imageBase64) {
        float[] features = extractFeature(imageBase64);
        return floatArrayToBase64(features);
    }

    public float[] extractFeatureArray(String imageBase64) {
        return extractFeature(imageBase64);
    }

    public float compare(String sourceImageBase64, String targetImageBase64) {
        float[] sourceFeatures = extractFeature(sourceImageBase64);
        float[] targetFeatures = extractFeature(targetImageBase64);
        return faceRecognizer.CalculateSimilarity(sourceFeatures, targetFeatures);
    }

    public float compare(float[] sourceFeatures, float[] targetFeatures) {
        return faceRecognizer.CalculateSimilarity(sourceFeatures, targetFeatures);
    }

    private float[] extractFeature(String imageBase64) {
        if (!StringUtils.hasText(imageBase64)) {
            throw new BizException("人脸图片 base64 不能为空");
        }

        try {
            byte[] imageBytes = decodeImageBytes(imageBase64);
            BufferedImage bufferedImage = javax.imageio.ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (bufferedImage == null) {
                throw new BizException(ResultCode.FACE_IMAGE_INVALID);
            }

            SeetaImageData imageData = SeetafaceUtil.toSeetaImageData(bufferedImage);
            SeetaRect[] faces = faceDetector.Detect(imageData);
            if (faces == null || faces.length == 0) {
                throw new BizException(ResultCode.FACE_NOT_DETECTED);
            }

            SeetaPointF[] points = new SeetaPointF[faceLandmarker.number()];
            int[] masks = new int[faceLandmarker.number()];
            faceLandmarker.mark(imageData, faces[0], points, masks);

            float[] features = new float[faceRecognizer.GetExtractFeatureSize()];
            boolean success = faceRecognizer.Extract(imageData, points, features);
            if (!success) {
                throw new BizException(ResultCode.FACE_FEATURE_EXTRACT_FAILED);
            }
            return features;
        } catch (BizException exception) {
            throw exception;
        } catch (Exception exception) {
            throw ResultCode.FACE_FEATURE_EXTRACT_FAILED.newException(exception, exception.getMessage());
        }
    }

    private byte[] decodeImageBytes(String imageBase64) {
        String trimmed = imageBase64.trim();
        if (trimmed.startsWith("data:")) {
            return DataUrlUtils.parseDataUrl(trimmed).getData();
        }
        return Base64.decode(trimmed);
    }

    private String prepareModelPath(String fileName) throws IOException {
        File sourceModelFile = resolveModelFile(fileName);
        File tempModelDir = new File(System.getProperty("java.io.tmpdir"), "seeta-face-models");
        if (!tempModelDir.exists() && !tempModelDir.mkdirs()) {
            throw new IOException("create temp model directory failed: " + tempModelDir.getAbsolutePath());
        }

        File targetModelFile = new File(tempModelDir, fileName);
        if (!targetModelFile.exists()
                || targetModelFile.length() != sourceModelFile.length()
                || targetModelFile.lastModified() < sourceModelFile.lastModified()) {
            Files.copy(sourceModelFile.toPath(), targetModelFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return targetModelFile.getAbsolutePath();
    }

    private File resolveModelFile(String fileName) {
        File modelDir = new File(faceEngineProperties.getModelDir());
        File modelFile = modelDir.isAbsolute() ? new File(modelDir, fileName) : new File(System.getProperty("user.dir"), faceEngineProperties.getModelDir() + File.separator + fileName);
        if (!modelFile.exists() || !modelFile.isFile()) {
            throw ResultCode.FACE_MODEL_NOT_FOUND.newException(modelFile.getAbsolutePath());
        }
        return modelFile;
    }

    private String floatArrayToBase64(float[] floats) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(floats.length * 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.asFloatBuffer().put(floats);
        return java.util.Base64.getEncoder().encodeToString(byteBuffer.array());
    }
}
