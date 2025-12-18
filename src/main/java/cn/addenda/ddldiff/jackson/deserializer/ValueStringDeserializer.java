package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueString;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueStringDeserializer extends JsonDeserializer<ValueString> {

  @Override
  public ValueString deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.asText();
    if (s == null || s.isEmpty() || "null".equals(s)) {
      return ValueString.of();
    }

    return ValueString.of(s);
  }

  @Override
  public ValueString getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueString.of();
  }

}
