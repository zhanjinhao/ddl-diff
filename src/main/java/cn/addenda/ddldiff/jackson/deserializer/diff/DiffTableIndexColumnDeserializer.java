package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.diff.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class DiffTableIndexColumnDeserializer extends AbstractDiffJsonDeserializer<DiffTableIndexColumn> {

  private static final TypeReference<DiffValueName> TR_DIFF_VALUE_NAME = new TypeReference<DiffValueName>() {
  };
  private static final TypeReference<DiffValueOrder> TR_DIFF_VALUE_ORDER = new TypeReference<DiffValueOrder>() {
  };

  @Override
  public DiffTableIndexColumn deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return DiffTableIndexColumn.NULL;
      } else {
        throw from(jp, jsonNode, DiffTableIndexColumn.class);
      }
    }
    ObjectNode obj = (ObjectNode) jsonNode;

    DiffValueName diffName = null;
    DiffValueOrder diffOrder = null;
    JsonNode n = obj.get("diffName");
    if (n != null && !n.isNull()) {
      diffName = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_NAME);
    }
    n = obj.get("diffOrder");
    if (n != null && !n.isNull()) {
      diffOrder = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_ORDER);
    }
    return DiffTableIndexColumn.of(diffName, diffOrder);
  }

  @Override
  public DiffTableIndexColumn getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffTableIndexColumn.NULL;
  }
}
