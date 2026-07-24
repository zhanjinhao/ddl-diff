package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.jackson.deserializer.AbstractJsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class AbstractDiffJsonDeserializer<T extends Diff>
        extends AbstractJsonDeserializer<T> {

  protected boolean ifNull(JsonNode jsonNode) {
    if (jsonNode.isNull()) {
      return true;
    }
    if (jsonNode.isTextual()) {
      String text = jsonNode.asText();
      if ("null".equals(text) || text.isEmpty() || Diff.EQUALS.equals(quote(text))) {
        return true;
      }
    }
    return false;
  }

  private String quote(String s) {
    return "\"" + s + "\"";
  }

}
