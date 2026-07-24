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

public class DiffTableIndexDeserializer extends AbstractDiffJsonDeserializer<DiffTableIndex> {

  private static final TypeReference<ComparedKey> TR_COMPARED_KEY = new TypeReference<ComparedKey>() {
  };
  private static final TypeReference<DiffTableIndexColumns> TR_DIFF_TABLE_INDEX_COLUMNS = new TypeReference<DiffTableIndexColumns>() {
  };
  private static final TypeReference<DiffValueName> TR_DIFF_VALUE_NAME = new TypeReference<DiffValueName>() {
  };
  private static final TypeReference<DiffTableIndexType> TR_DIFF_TABLE_INDEX_TYPE = new TypeReference<DiffTableIndexType>() {
  };
  private static final TypeReference<DiffValueComment> TR_DIFF_VALUE_COMMENT = new TypeReference<DiffValueComment>() {
  };

  @Override
  public DiffTableIndex deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return DiffTableIndex.NULL;
      } else {
        throw from(jp, jsonNode, DiffTableIndex.class);
      }
    }
    ObjectNode obj = (ObjectNode) jsonNode;

    ComparedKey comparedKey = null;
    DiffTableIndexColumns diffTableIndexColumns = null;
    DiffValueName diffName = null;
    DiffTableIndexType diffIndexType = null;
    DiffValueComment diffComment = null;
    JsonNode n = obj.get("indexName");
    if (n != null && !n.isNull()) {
      comparedKey = JacksonUtils.toObj(n.toString(), TR_COMPARED_KEY);
    }
    n = obj.get("diffTableIndexColumns");
    if (n != null && !n.isNull()) {
      diffTableIndexColumns = JacksonUtils.toObj(n.toString(), TR_DIFF_TABLE_INDEX_COLUMNS);
    }
    n = obj.get("diffName");
    if (n != null && !n.isNull()) {
      diffName = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_NAME);
    }
    n = obj.get("diffIndexType");
    if (n != null && !n.isNull()) {
      diffIndexType = JacksonUtils.toObj(n.toString(), TR_DIFF_TABLE_INDEX_TYPE);
    }
    n = obj.get("diffComment");
    if (n != null && !n.isNull()) {
      diffComment = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_COMMENT);
    }
    return DiffTableIndex.of(comparedKey, diffTableIndexColumns, diffName, diffIndexType, diffComment);
  }

  @Override
  public DiffTableIndex getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffTableIndex.NULL;
  }
}
