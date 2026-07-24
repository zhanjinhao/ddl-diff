package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueOrderDeserializer extends AbstractDiffAbleJsonDeserializer<ValueOrder> {

  @Override
  public ValueOrder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (ifNull(jsonNode)) {
      return ValueOrder.of();
    } else {
      if (jsonNode.isTextual()) {
        return ValueOrder.of(jsonNode.asText());
      }
    }
    throw from(jp, jsonNode, ValueOrder.class);
  }

  @Override
  public ValueOrder getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueOrder.of();
  }

}
