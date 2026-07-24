package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.DiffAble;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class AbstractDiffAbleJsonDeserializer<T extends DiffAble<T, ?>>
        extends AbstractJsonDeserializer<T> {

  protected boolean ifNull(JsonNode jsonNode) {
    if (jsonNode.isNull()) {
      return true;
    }
    if (jsonNode.isTextual()) {
      String text = jsonNode.asText();
      if ("null".equals(text) || text.isEmpty()) {
        return true;
      }
    }
    return false;
  }

}
