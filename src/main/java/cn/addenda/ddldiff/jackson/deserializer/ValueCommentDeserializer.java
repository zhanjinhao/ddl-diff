package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueComment;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueCommentDeserializer extends JsonDeserializer<ValueComment> {

  @Override
  public ValueComment deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.asText();
    if (s == null || s.isEmpty() || "null".equals(s)) {
      return ValueComment.of();
    }

    return ValueComment.of(s);
  }

  @Override
  public ValueComment getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueComment.of();
  }

}
