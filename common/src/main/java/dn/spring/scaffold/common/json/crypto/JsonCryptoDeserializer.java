package dn.spring.scaffold.common.json.crypto;

import dn.spring.scaffold.common.encryptor.Encryptor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class JsonCryptoDeserializer extends JsonDeserializer<Object> {

    private final JsonDeserializer<Object> delegate;

    private final Encryptor encryptor;

    public JsonCryptoDeserializer(JsonDeserializer<Object> delegate, Encryptor encryptor) {
        this.delegate = delegate;
        this.encryptor = encryptor;
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (value == null) {
            return delegate.deserialize(p, ctxt);
        }
        return encryptor.decrypt(value);
    }
}
