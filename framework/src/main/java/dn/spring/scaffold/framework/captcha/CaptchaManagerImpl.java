package dn.spring.scaffold.framework.captcha;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CaptchaManagerImpl implements CaptchaManager {

    @Resource
    private CaptchaProperties properties;

    @Resource
    private CaptchaCache captchaCache;

    @Override
    public Captcha generate() {
        CircleCaptcha circleCaptcha = new CircleCaptcha(
                properties.getWidth(),
                properties.getHeight(),
                properties.getCodeCount(),
                properties.getCircleCount()
        );
        String code = circleCaptcha.getCode();
        String uuid = IdUtil.fastSimpleUUID();
        captchaCache.put(uuid, code, properties.getExpireSeconds());

        Captcha captcha = new Captcha();
        captcha.setUuid(uuid);
        captcha.setCode(code);
        captcha.setExpireSeconds(properties.getExpireSeconds());
        captcha.setImg("data:image/png;base64," + Base64.encode(circleCaptcha.getImageBytes()));
        return captcha;
    }

    @Override
    public boolean exists(String uuid) {
        return StrUtil.isNotBlank(uuid) && StrUtil.isNotBlank(captchaCache.get(uuid));
    }

    @Override
    public boolean verify(String uuid, String code, boolean removeAfterSuccess) {
        if (StrUtil.hasBlank(uuid, code)) {
            return false;
        }
        String cachedCode = captchaCache.get(uuid);
        if (!StrUtil.equalsIgnoreCase(cachedCode, code)) {
            return false;
        }
        if (removeAfterSuccess) {
            captchaCache.remove(uuid);
        }
        return true;
    }
}
