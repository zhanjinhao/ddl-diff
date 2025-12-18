package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueOrderDeserializer extends JsonDeserializer<ValueOrder> {

  @Override
  public ValueOrder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.asText();
    if (s == null || s.isEmpty() || "null".equals(s)) {
      return ValueOrder.of();
    }

    return ValueOrder.of(s);
  }

  @Override
  public ValueOrder getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueOrder.of();
  }

}
