package dn.spring.scaffold.common.json.crypto;

import dn.spring.scaffold.common.annotation.JsonCrypto;
import dn.spring.scaffold.common.encryptor.Encryptor;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

import java.util.Iterator;

public class JsonCryptoDeserializerModifier extends BeanDeserializerModifier {

    private final Encryptor encryptor;

    public JsonCryptoDeserializerModifier(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    public BeanDeserializerBuilder updateBuilder(DeserializationConfig config,
                                                 BeanDescription beanDesc,
                                                 BeanDeserializerBuilder builder) {
        Iterator<BeanPropertyDefinition> properties = beanDesc.findProperties().iterator();
        while (properties.hasNext()) {
            BeanPropertyDefinition property = properties.next();
            if (property.getField() == null || property.getField().getAnnotation(JsonCrypto.class) == null) {
                continue;
            }
            PropertyName propertyName = PropertyName.construct(property.getName());
            SettableBeanProperty settableBeanProperty = builder.findProperty(propertyName);
            JsonDeserializer<Object> currentDeserializer = settableBeanProperty.getValueDeserializer();
            builder.addOrReplaceProperty(
                    settableBeanProperty.withValueDeserializer(
                            new JsonCryptoDeserializer(currentDeserializer, encryptor)
                    ),
                    true
            );
        }
        return builder;
    }
}
