package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueBoolean;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueBooleanDeserializer extends JsonDeserializer<ValueBoolean> {

  @Override
  public ValueBoolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.asText();
    if (s == null || s.isEmpty()) {
      throw new IllegalArgumentException("only support 'true' and 'false'.");
    }

    if ("false".equalsIgnoreCase(s)) {
      return ValueBoolean.FALSE;
    }

    if ("true".equalsIgnoreCase(s)) {
      return ValueBoolean.TRUE;
    }

    throw new IllegalArgumentException("only support 'true' and 'false'.");
  }

  @Override
  public ValueBoolean getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    throw new IllegalArgumentException("only support 'true' and 'false'.");
  }

}
