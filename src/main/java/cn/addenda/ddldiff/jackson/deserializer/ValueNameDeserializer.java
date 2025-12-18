package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueName;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueNameDeserializer extends JsonDeserializer<ValueName> {

  @Override
  public ValueName deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.asText();
    if (s == null || s.isEmpty() || "null".equals(s)) {
      return ValueName.of();
    }

    return ValueName.of(s);
  }

  @Override
  public ValueName getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueName.of();
  }

}
