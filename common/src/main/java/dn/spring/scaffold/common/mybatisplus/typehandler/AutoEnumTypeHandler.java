package dn.spring.scaffold.common.mybatisplus.typehandler;

import dn.spring.scaffold.common.entity.CodeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(CodeEnum.class)
public class AutoEnumTypeHandler<E extends Enum<E> & CodeEnum<?>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public AutoEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return resolveEnum(rs.getObject(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return resolveEnum(rs.getObject(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return resolveEnum(cs.getObject(columnIndex));
    }

    private E resolveEnum(Object code) {
        if (code == null) {
            return null;
        }
        for (E enumConstant : type.getEnumConstants()) {
            Object enumCode = enumConstant.getCode();
            if (enumCode != null && String.valueOf(enumCode).equals(String.valueOf(code))) {
                return enumConstant;
            }
        }
        return null;
    }
}
