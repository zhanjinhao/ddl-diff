package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.diff.DiffValueBoolean;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.LinkedHashMap;

public class DiffValueBooleanDeserializer extends JsonDeserializer<DiffValueBoolean> {

  private static final TypeReference<LinkedHashMap<String, Boolean>> typeReference = new TypeReference<LinkedHashMap<String, Boolean>>() {
  };

  @Override
  public DiffValueBoolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.toString();
    if (s == null || s.isEmpty() || "null".equals(s)) {
      return DiffValueBoolean.NULL;
    }

    LinkedHashMap<String, Boolean> map = JacksonUtils.toObj(s, typeReference);

    return DiffValueBoolean.of(
            map.get(EnvContext.getSourceName()),
            map.get(EnvContext.getTargetName()));
  }

  @Override
  public DiffValueBoolean getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffValueBoolean.NULL;
  }

}
