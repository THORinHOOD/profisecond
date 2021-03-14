package com.profi.second;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class NoteJsonSerializer extends JsonSerializer<Note> {

    private final int N;

    public NoteJsonSerializer(MainConfiguration mainConfiguration) {
        N = mainConfiguration.getN();
    }

    @Override
    public void serialize(Note value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", value.getId());
        jsonGenerator.writeStringField("content", value.getContent());
        if (value.getTitle() == null) {
            jsonGenerator.writeStringField("title", value.getContent().substring(0, N));
        } else {
            jsonGenerator.writeStringField("title", value.getTitle());
        }
        jsonGenerator.writeEndObject();
    }



}
