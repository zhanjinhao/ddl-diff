package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.diff.DiffValueName;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.LinkedHashMap;

public class DiffValueNameDeserializer extends AbstractDiffJsonDeserializer<DiffValueName> {

  private static final TypeReference<LinkedHashMap<String, String>> typeReference = new TypeReference<LinkedHashMap<String, String>>() {
  };

  @Override
  public DiffValueName deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return DiffValueName.NULL;
      } else {
        throw from(jp, jsonNode, DiffValueName.class);
      }
    }
    final String s = jsonNode.toString();

    LinkedHashMap<String, String> map = JacksonUtils.toObj(s, typeReference);

    return DiffValueName.of(map.get(EnvContext.getSourceName()), map.get(EnvContext.getTargetName()));
  }

  @Override
  public DiffValueName getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffValueName.NULL;
  }

}
