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

public class DiffTableDeserializer extends AbstractDiffJsonDeserializer<DiffTable> {

  private static final TypeReference<ComparedKey> TR_COMPARED_KEY = new TypeReference<ComparedKey>() {
  };
  private static final TypeReference<DiffValueName> TR_DIFF_VALUE_NAME = new TypeReference<DiffValueName>() {
  };
  private static final TypeReference<DiffTableColumns> TR_DIFF_TABLE_COLUMNS = new TypeReference<DiffTableColumns>() {
  };
  private static final TypeReference<DiffTableIndexes> TR_DIFF_TABLE_INDEXES = new TypeReference<DiffTableIndexes>() {
  };
  private static final TypeReference<DiffValueString> TR_DIFF_VALUE_STRING = new TypeReference<DiffValueString>() {
  };
  private static final TypeReference<DiffValueComment> TR_DIFF_VALUE_COMMENT = new TypeReference<DiffValueComment>() {
  };

  @Override
  public DiffTable deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return DiffTable.NULL;
      } else {
        throw from(jp, jsonNode, DiffTable.class);
      }
    }
    ObjectNode obj = (ObjectNode) jsonNode;

    ComparedKey comparedKey = null;
    DiffValueName diffName = null;
    DiffTableColumns diffTableColumns = null;
    DiffTableIndexes diffTableIndexes = null;
    DiffValueString diffEngine = null;
    DiffValueString diffCharset = null;
    DiffValueString diffCollate = null;
    DiffValueComment diffComment = null;
    JsonNode n = obj.get("tableName");
    if (n != null && !n.isNull()) {
      comparedKey = JacksonUtils.toObj(n.toString(), TR_COMPARED_KEY);
    }
    n = obj.get("diffName");
    if (n != null && !n.isNull()) {
      diffName = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_NAME);
    }
    n = obj.get("diffTableColumns");
    if (n != null && !n.isNull()) {
      diffTableColumns = JacksonUtils.toObj(n.toString(), TR_DIFF_TABLE_COLUMNS);
    }
    n = obj.get("diffTableIndexes");
    if (n != null && !n.isNull()) {
      diffTableIndexes = JacksonUtils.toObj(n.toString(), TR_DIFF_TABLE_INDEXES);
    }
    n = obj.get("diffEngine");
    if (n != null && !n.isNull()) {
      diffEngine = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_STRING);
    }
    n = obj.get("diffCharset");
    if (n != null && !n.isNull()) {
      diffCharset = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_STRING);
    }
    n = obj.get("diffCollate");
    if (n != null && !n.isNull()) {
      diffCollate = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_STRING);
    }
    n = obj.get("diffComment");
    if (n != null && !n.isNull()) {
      diffComment = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_COMMENT);
    }
    return DiffTable.of(comparedKey, diffName, diffTableColumns, diffTableIndexes,
            diffEngine, diffCharset, diffCollate, diffComment);
  }

  @Override
  public DiffTable getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffTable.NULL;
  }

}
