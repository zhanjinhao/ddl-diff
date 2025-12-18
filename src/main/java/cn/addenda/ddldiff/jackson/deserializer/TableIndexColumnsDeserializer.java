package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.TableIndexColumn;
import cn.addenda.ddldiff.bo.TableIndexColumns;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableIndexColumnsDeserializer extends JsonDeserializer<TableIndexColumns> {

  private static final TypeReference<TableIndexColumn> typeReference = new TypeReference<TableIndexColumn>() {
  };

  @Override
  public TableIndexColumns deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    ArrayNode arrayNode = jp.getCodec().readTree(jp);

    List<JsonNode> jsonNodeList = new ArrayList<>();
    for (JsonNode next : arrayNode) {
      jsonNodeList.add(next);
    }

    if (jsonNodeList.isEmpty()) {
      return TableIndexColumns.of();
    }

    TableIndexColumns of = TableIndexColumns.of();
    for (JsonNode jsonNode : jsonNodeList) {
      of.addTableIndexColumn(JacksonUtils.toObj(jsonNode.toString(), typeReference));
    }

    return of;
  }

  @Override
  public TableIndexColumns getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return TableIndexColumns.of();
  }

}
