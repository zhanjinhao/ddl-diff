package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueName;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueNameDeserializer extends AbstractDiffAbleJsonDeserializer<ValueName> {

  @Override
  public ValueName deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (ifNull(jsonNode)) {
      return ValueName.of();
    } else {
      if (jsonNode.isTextual()) {
        return ValueName.of(jsonNode.asText());
      }
    }
    throw from(jp, jsonNode, ValueName.class);
  }

  @Override
  public ValueName getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueName.of();
  }

}
