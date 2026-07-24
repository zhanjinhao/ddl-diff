package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueTypeDeserializer extends AbstractDiffAbleJsonDeserializer<ValueType> {

  @Override
  public ValueType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (ifNull(jsonNode)) {
      return ValueType.of();
    } else {
      if (jsonNode.isTextual()) {
        return ValueType.of(jsonNode.asText());
      }
    }
    throw from(jp, jsonNode, ValueType.class);
  }

  @Override
  public ValueType getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueType.of();
  }

}
