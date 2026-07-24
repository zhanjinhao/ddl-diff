package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class TableColumnDeserializer extends AbstractDiffAbleJsonDeserializer<TableColumn> {

  private static final TypeReference<ValueName> TR_VALUE_NAME = new TypeReference<ValueName>() {
  };
  private static final TypeReference<ValueType> TR_VALUE_TYPE = new TypeReference<ValueType>() {
  };
  private static final TypeReference<ValueString> TR_VALUE_STRING = new TypeReference<ValueString>() {
  };
  private static final TypeReference<ValueBoolean> TR_VALUE_BOOLEAN = new TypeReference<ValueBoolean>() {
  };
  private static final TypeReference<ValueComment> TR_VALUE_COMMENT = new TypeReference<ValueComment>() {
  };

  @Override
  public TableColumn deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return TableColumn.of();
      } else {
        throw from(jp, jsonNode, TableColumn.class);
      }
    }
    ObjectNode obj = (ObjectNode) jsonNode;

    TableColumn.TableColumnBuilder builder = TableColumn.builder();
    JsonNode n = obj.get("name");
    if (n != null && !n.isNull()) {
      builder.name(JacksonUtils.toObj(n.toString(), TR_VALUE_NAME));
    }
    n = obj.get("type");
    if (n != null && !n.isNull()) {
      builder.type(JacksonUtils.toObj(n.toString(), TR_VALUE_TYPE));
    }
    n = obj.get("charset");
    if (n != null && !n.isNull()) {
      builder.charset(JacksonUtils.toObj(n.toString(), TR_VALUE_STRING));
    }
    n = obj.get("collate");
    if (n != null && !n.isNull()) {
      builder.collate(JacksonUtils.toObj(n.toString(), TR_VALUE_STRING));
    }
    n = obj.get("defaultValue");
    if (n != null && !n.isNull()) {
      builder.defaultValue(JacksonUtils.toObj(n.toString(), TR_VALUE_STRING));
    }
    n = obj.get("ifNullable");
    if (n != null && !n.isNull()) {
      builder.ifNullable(JacksonUtils.toObj(n.toString(), TR_VALUE_BOOLEAN));
    }
    n = obj.get("ifAutoIncrement");
    if (n != null && !n.isNull()) {
      builder.ifAutoIncrement(JacksonUtils.toObj(n.toString(), TR_VALUE_BOOLEAN));
    }
    n = obj.get("comment");
    if (n != null && !n.isNull()) {
      builder.comment(JacksonUtils.toObj(n.toString(), TR_VALUE_COMMENT));
    }
    return builder.build();
  }

  @Override
  public TableColumn getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return TableColumn.of();
  }
}
