package dn.spring.scaffold.console.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project.face")
public class FaceEngineProperties {

    /**
     * Seeta 模型目录，默认指向项目根目录下的 model。
     */
    private String modelDir = "model";

    /**
     * 人脸检测模型文件名。
     */
    private String detectorModel = "face_detector.csta";

    /**
     * 五点关键点模型文件名。
     */
    private String landmarkerModel = "face_landmarker_pts5.csta";

    /**
     * 人脸特征提取模型文件名。
     */
    private String recognizerModel = "face_recognizer_light.csta";
}
