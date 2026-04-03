package dn.spring.scaffold.common.encryptor;

public interface Encryptor {

    String encrypt(String plainData);

    String decrypt(String encryptedData);
}
