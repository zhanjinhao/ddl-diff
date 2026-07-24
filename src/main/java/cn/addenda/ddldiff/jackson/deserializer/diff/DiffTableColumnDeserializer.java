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

public class DiffTableColumnDeserializer extends AbstractDiffJsonDeserializer<DiffTableColumn> {

  private static final TypeReference<ComparedKey> TR_COMPARED_KEY = new TypeReference<ComparedKey>() {
  };
  private static final TypeReference<DiffValueName> TR_DIFF_VALUE_NAME = new TypeReference<DiffValueName>() {
  };
  private static final TypeReference<DiffValueType> TR_DIFF_VALUE_TYPE = new TypeReference<DiffValueType>() {
  };
  private static final TypeReference<DiffValueString> TR_DIFF_VALUE_STRING = new TypeReference<DiffValueString>() {
  };
  private static final TypeReference<DiffValueBoolean> TR_DIFF_VALUE_BOOLEAN = new TypeReference<DiffValueBoolean>() {
  };
  private static final TypeReference<DiffValueComment> TR_DIFF_VALUE_COMMENT = new TypeReference<DiffValueComment>() {
  };

  @Override
  public DiffTableColumn deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return DiffTableColumn.NULL;
      } else {
        throw from(jp, jsonNode, DiffTableColumn.class);
      }
    }
    ObjectNode obj = (ObjectNode) jsonNode;

    ComparedKey comparedKey = null;
    DiffValueName diffName = null;
    DiffValueType diffType = null;
    DiffValueString diffCharset = null;
    DiffValueString diffCollate = null;
    DiffValueString diffDefaultValue = null;
    DiffValueBoolean diffIfNullable = null;
    DiffValueBoolean diffIfAutoIncrement = null;
    DiffValueComment diffComment = null;
    JsonNode n = obj.get("columnName");
    if (n != null && !n.isNull()) {
      comparedKey = JacksonUtils.toObj(n.toString(), TR_COMPARED_KEY);
    }
    n = obj.get("diffName");
    if (n != null && !n.isNull()) {
      diffName = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_NAME);
    }
    n = obj.get("diffType");
    if (n != null && !n.isNull()) {
      diffType = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_TYPE);
    }
    n = obj.get("diffCharset");
    if (n != null && !n.isNull()) {
      diffCharset = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_STRING);
    }
    n = obj.get("diffCollate");
    if (n != null && !n.isNull()) {
      diffCollate = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_STRING);
    }
    n = obj.get("diffDefaultValue");
    if (n != null && !n.isNull()) {
      diffDefaultValue = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_STRING);
    }
    n = obj.get("diffIfNullable");
    if (n != null && !n.isNull()) {
      diffIfNullable = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_BOOLEAN);
    }
    n = obj.get("diffIfAutoIncrement");
    if (n != null && !n.isNull()) {
      diffIfAutoIncrement = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_BOOLEAN);
    }
    n = obj.get("diffComment");
    if (n != null && !n.isNull()) {
      diffComment = JacksonUtils.toObj(n.toString(), TR_DIFF_VALUE_COMMENT);
    }
    return DiffTableColumn.of(comparedKey, diffName, diffType,
            diffCharset, diffCollate, diffDefaultValue,
            diffIfNullable, diffIfAutoIncrement, diffComment);
  }

  @Override
  public DiffTableColumn getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffTableColumn.NULL;
  }

}
