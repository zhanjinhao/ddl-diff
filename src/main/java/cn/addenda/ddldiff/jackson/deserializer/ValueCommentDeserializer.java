package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.ValueComment;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValueCommentDeserializer extends AbstractDiffAbleJsonDeserializer<ValueComment> {

  @Override
  public ValueComment deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (ifNull(jsonNode)) {
      return ValueComment.of();
    } else {
      if (jsonNode.isTextual()) {
        return ValueComment.of(jsonNode.asText());
      }
    }

    throw from(jp, jsonNode, ValueComment.class);
  }

  @Override
  public ValueComment getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return ValueComment.of();
  }

}
