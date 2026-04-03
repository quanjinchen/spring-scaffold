package dn.spring.scaffold.framework.captcha;

public interface CaptchaManager {

    Captcha generate();

    boolean exists(String uuid);

    boolean verify(String uuid, String code, boolean removeAfterSuccess);
}
