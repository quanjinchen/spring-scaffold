package dn.spring.scaffold.common.mybatisplus.typehandler;

import dn.spring.scaffold.common.entity.EncryptField;
import dn.spring.scaffold.common.encryptor.Encryptor;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(EncryptField.class)
public class EncryptTypeHandler extends BaseTypeHandler<EncryptField> {

    private final Encryptor encryptor;

    public EncryptTypeHandler(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EncryptField parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, doEncrypt(parameter.getPlainText()));
    }

    @Override
    public EncryptField getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return buildEncryptField(rs.getString(columnName));
    }

    @Override
    public EncryptField getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return buildEncryptField(rs.getString(columnIndex));
    }

    @Override
    public EncryptField getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return buildEncryptField(cs.getString(columnIndex));
    }

    private EncryptField buildEncryptField(String encryptedText) {
        String plainText = doDecrypt(encryptedText);
        return plainText == null && encryptedText == null ? null : new EncryptField(plainText, encryptedText);
    }

    private String doEncrypt(String plainValue) {
        if (!StringUtils.hasText(plainValue)) {
            return null;
        }
        return encryptor.encrypt(plainValue);
    }

    private String doDecrypt(String encryptedValue) {
        if (!StringUtils.hasText(encryptedValue)) {
            return null;
        }
        return encryptor.decrypt(encryptedValue);
    }
}
