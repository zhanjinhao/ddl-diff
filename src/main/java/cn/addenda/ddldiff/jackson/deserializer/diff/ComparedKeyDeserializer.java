package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.ddldiff.bo.diff.ComparedKey;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ComparedKeyDeserializer extends JsonDeserializer<ComparedKey> {

  @Override
  public ComparedKey deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.asText();
    if (s == null || s.isEmpty() || "null".equals(s)) {
      throw new IllegalArgumentException("do not support null key.");
    }
    String[] split = s.split("-");
    return ComparedKey.of(
            split[0].substring(0, split[0].indexOf("(")),
            split[1].substring(0, split[1].indexOf("(")));
  }

}
