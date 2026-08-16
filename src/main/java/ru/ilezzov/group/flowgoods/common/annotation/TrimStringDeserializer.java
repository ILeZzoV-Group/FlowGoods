package ru.ilezzov.group.flowgoods.common.annotation;


import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

public class TrimStringDeserializer extends StdDeserializer<String> {

    public TrimStringDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(final JsonParser p, final DeserializationContext ctxt) {
        final String text = p.getString();

        if (text == null) {
            return null;
        }

        return text.strip();
    }
}
