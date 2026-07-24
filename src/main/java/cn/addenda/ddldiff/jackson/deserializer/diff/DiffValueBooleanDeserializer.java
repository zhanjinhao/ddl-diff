package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.diff.DiffValueBoolean;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.LinkedHashMap;

public class DiffValueBooleanDeserializer extends AbstractDiffJsonDeserializer<DiffValueBoolean> {

  private static final TypeReference<LinkedHashMap<String, Boolean>> typeReference = new TypeReference<LinkedHashMap<String, Boolean>>() {
  };

  @Override
  public DiffValueBoolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return DiffValueBoolean.NULL;
      } else {
        throw from(jp, jsonNode, DiffValueBoolean.class);
      }
    }
    final String s = jsonNode.toString();
    LinkedHashMap<String, Boolean> map = JacksonUtils.toObj(s, typeReference);

    Boolean source = map.get(EnvContext.getSourceName());
    Boolean target = map.get(EnvContext.getTargetName());
    if (source == null || target == null) {
      throw new IllegalArgumentException(
              String.format("only support 'true' and 'false'. source: %s, target: %s", source, target));
    }
    return DiffValueBoolean.of(source, target);
  }

  @Override
  public DiffValueBoolean getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffValueBoolean.NULL;
  }

}
