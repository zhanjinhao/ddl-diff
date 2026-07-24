package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueString;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueStringDeserializer extends AbstractDiffAbleJsonDeserializer<ValueString> {

  @Override
  public ValueString deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (ifNull(jsonNode)) {
      return ValueString.of();
    } else {
      if (jsonNode.isTextual()) {
        return ValueString.of(jsonNode.asText());
      }
    }
    throw from(jp, jsonNode, ValueString.class);
  }

  @Override
  public ValueString getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueString.of();
  }

}
