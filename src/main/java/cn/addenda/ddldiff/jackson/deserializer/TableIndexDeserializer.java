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

public class TableIndexDeserializer extends AbstractDiffAbleJsonDeserializer<TableIndex> {

  private static final TypeReference<TableIndexColumns> TR_TABLE_INDEX_COLUMNS = new TypeReference<TableIndexColumns>() {
  };
  private static final TypeReference<ValueName> TR_VALUE_NAME = new TypeReference<ValueName>() {
  };
  private static final TypeReference<ValueComment> TR_VALUE_COMMENT = new TypeReference<ValueComment>() {
  };

  @Override
  public TableIndex deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isObject()) {
      if (ifNull(jsonNode)) {
        return TableIndex.of();
      } else {
        throw from(jp, jsonNode, TableIndex.class);
      }
    }
    ObjectNode obj = (ObjectNode) jsonNode;

    TableIndex.TableIndexBuilder builder = TableIndex.builder();
    JsonNode n = obj.get("tableIndexColumns");
    if (n != null && !n.isNull()) {
      builder.tableIndexColumns(JacksonUtils.toObj(n.toString(), TR_TABLE_INDEX_COLUMNS));
    }
    n = obj.get("name");
    if (n != null && !n.isNull()) {
      builder.name(JacksonUtils.toObj(n.toString(), TR_VALUE_NAME));
    }
    n = obj.get("indexType");
    if (n != null && !n.isNull()) {
      builder.indexType(IndexType.valueOf(n.asText()));
    }
    n = obj.get("comment");
    if (n != null && !n.isNull()) {
      builder.comment(JacksonUtils.toObj(n.toString(), TR_VALUE_COMMENT));
    }
    return builder.build();
  }

  @Override
  public TableIndex getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return TableIndex.of();
  }
}
