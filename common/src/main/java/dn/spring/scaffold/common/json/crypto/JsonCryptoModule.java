package dn.spring.scaffold.common.json.crypto;

import dn.spring.scaffold.common.encryptor.Encryptor;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JsonCryptoModule extends SimpleModule {

    public JsonCryptoModule(Encryptor encryptor) {
        super("JsonCryptoModule", Version.unknownVersion());
        setSerializerModifier(new JsonCryptoSerializerModifier(encryptor));
        setDeserializerModifier(new JsonCryptoDeserializerModifier(encryptor));
    }
}
