package dn.spring.scaffold.common.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EncryptField {

    private final String plainText;

    private String encryptedText;

    public EncryptField(String plainText) {
        this.plainText = plainText;
    }

    public EncryptField(String plainText, String encryptedText) {
        this.plainText = plainText;
        this.encryptedText = encryptedText;
    }

    public static String toPlainText(EncryptField encryptField) {
        if (encryptField == null) {
            return null;
        }
        return encryptField.getPlainText();
    }
}
