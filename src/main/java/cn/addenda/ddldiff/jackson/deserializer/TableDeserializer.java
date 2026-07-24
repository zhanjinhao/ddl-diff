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

public class TableDeserializer extends AbstractDiffAbleJsonDeserializer<Table> {

  private static final TypeReference<ValueName> TR_VALUE_NAME = new TypeReference<ValueName>() {
  };
  private static final TypeReference<TableColumns> TR_TABLE_COLUMNS = new TypeReference<TableColumns>() {
  };
  private static final TypeReference<TableIndexes> TR_TABLE_INDEXES = new TypeReference<TableIndexes>() {
  };
  private static final TypeReference<ValueString> TR_VALUE_STRING = new TypeReference<ValueString>() {
  };
  private static final TypeReference<ValueComment> TR_VALUE_COMMENT = new TypeReference<ValueComment>() {
  };

  @Override
  public Table deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return Table.builder().build();
      } else {
        throw from(jp, jsonNode, Table.class);
      }
    }
    ObjectNode obj = (ObjectNode) jsonNode;

    Table.TableBuilder builder = Table.builder();
    JsonNode n = obj.get("name");
    if (n != null && !n.isNull()) {
      builder.name(JacksonUtils.toObj(n.toString(), TR_VALUE_NAME));
    }
    n = obj.get("tableColumns");
    if (n != null && !n.isNull()) {
      builder.tableColumns(JacksonUtils.toObj(n.toString(), TR_TABLE_COLUMNS));
    }
    n = obj.get("tableIndexes");
    if (n != null && !n.isNull()) {
      builder.tableIndexes(JacksonUtils.toObj(n.toString(), TR_TABLE_INDEXES));
    }
    n = obj.get("engine");
    if (n != null && !n.isNull()) {
      builder.engine(JacksonUtils.toObj(n.toString(), TR_VALUE_STRING));
    }
    n = obj.get("charset");
    if (n != null && !n.isNull()) {
      builder.charset(JacksonUtils.toObj(n.toString(), TR_VALUE_STRING));
    }
    n = obj.get("collate");
    if (n != null && !n.isNull()) {
      builder.collate(JacksonUtils.toObj(n.toString(), TR_VALUE_STRING));
    }
    n = obj.get("comment");
    if (n != null && !n.isNull()) {
      builder.comment(JacksonUtils.toObj(n.toString(), TR_VALUE_COMMENT));
    }
    return builder.build();
  }

  @Override
  public Table getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return Table.builder().build();
  }

}
