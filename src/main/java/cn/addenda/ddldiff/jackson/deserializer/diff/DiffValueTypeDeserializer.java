package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.diff.DiffValueType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.LinkedHashMap;

public class DiffValueTypeDeserializer extends JsonDeserializer<DiffValueType> {

  private static final TypeReference<LinkedHashMap<String, String>> typeReference = new TypeReference<LinkedHashMap<String, String>>() {
  };

  @Override
  public DiffValueType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.toString();
    if (s == null || s.isEmpty() || "null".equals(s)) {
      return DiffValueType.NULL;
    }

    LinkedHashMap<String, String> map = JacksonUtils.toObj(s, typeReference);

    return DiffValueType.of(map.get(EnvContext.getSourceName()), map.get(EnvContext.getTargetName()));
  }

  @Override
  public DiffValueType getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffValueType.NULL;
  }

}
