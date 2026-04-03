package dn.spring.scaffold.common.json.crypto;

import dn.spring.scaffold.common.annotation.JsonCrypto;
import dn.spring.scaffold.common.encryptor.Encryptor;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;

public class JsonCryptoSerializerModifier extends BeanSerializerModifier {

    private final Encryptor encryptor;

    public JsonCryptoSerializerModifier(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                     BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter beanProperty : beanProperties) {
            if (beanProperty.getAnnotation(JsonCrypto.class) == null) {
                continue;
            }
            JsonSerializer<Object> serializer = new JsonCryptoSerializer(beanProperty.getSerializer(), encryptor);
            beanProperty.assignSerializer(serializer);
        }
        return beanProperties;
    }
}
