package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueTypeDeserializer extends JsonDeserializer<ValueType> {

  @Override
  public ValueType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.asText();
    if (s == null || s.isEmpty() || "null".equals(s)) {
      return ValueType.of();
    }

    return ValueType.of(s);
  }

  @Override
  public ValueType getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueType.of();
  }

}
