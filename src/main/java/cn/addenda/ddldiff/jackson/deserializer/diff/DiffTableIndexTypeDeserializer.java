package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.IndexType;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Optional;

public class DiffTableIndexTypeDeserializer extends AbstractDiffJsonDeserializer<DiffTableIndexType> {

  private static final TypeReference<LinkedHashMap<String, String>> typeReference = new TypeReference<LinkedHashMap<String, String>>() {
  };

  @Override
  public DiffTableIndexType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return DiffTableIndexType.NULL;
      } else {
        throw from(jp, jsonNode, DiffTableIndexType.class);
      }
    }
    final String s = jsonNode.toString();

    LinkedHashMap<String, String> map = JacksonUtils.toObj(s, typeReference);

    return DiffTableIndexType.of(
            Optional.ofNullable(map.get(EnvContext.getSourceName())).map(IndexType::valueOf).orElse(null),
            Optional.ofNullable(map.get(EnvContext.getTargetName())).map(IndexType::valueOf).orElse(null));
  }

  @Override
  public DiffTableIndexType getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffTableIndexType.NULL;
  }

}
