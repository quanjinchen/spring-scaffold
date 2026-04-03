package dn.spring.scaffold.common.encryptor;

import dn.spring.scaffold.common.utils.AesCbcUtils;
import org.springframework.util.StringUtils;

public class DefaultEncryptor implements Encryptor {

    private final String key;

    private final String iv;

    public DefaultEncryptor(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }

    @Override
    public String encrypt(String plainData) {
        if (!StringUtils.hasText(plainData)) {
            return null;
        }
        return AesCbcUtils.encryptToBase64(plainData, key, iv);
    }

    @Override
    public String decrypt(String encryptedData) {
        if (!StringUtils.hasText(encryptedData)) {
            return null;
        }
        return AesCbcUtils.decryptFromBase64(encryptedData, key, iv);
    }
}
