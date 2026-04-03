package dn.spring.scaffold.common.json.crypto;

import dn.spring.scaffold.common.encryptor.Encryptor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class JsonCryptoSerializer extends JsonSerializer<Object> {

    private final JsonSerializer<Object> delegate;

    private final Encryptor encryptor;

    public JsonCryptoSerializer(JsonSerializer<Object> delegate, Encryptor encryptor) {
        this.delegate = delegate;
        this.encryptor = encryptor;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            delegate.serialize(null, gen, serializers);
            return;
        }
        gen.writeString(encryptor.encrypt(String.valueOf(value)));
    }
}
