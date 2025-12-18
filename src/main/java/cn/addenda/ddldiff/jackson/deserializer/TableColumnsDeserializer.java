package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.TableColumn;
import cn.addenda.ddldiff.bo.TableColumns;
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

public class TableColumnsDeserializer extends JsonDeserializer<TableColumns> {

  private static final TypeReference<TableColumn> typeReference = new TypeReference<TableColumn>() {
  };

  @Override
  public TableColumns deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    ArrayNode arrayNode = jp.getCodec().readTree(jp);

    List<JsonNode> jsonNodeList = new ArrayList<>();
    for (JsonNode next : arrayNode) {
      jsonNodeList.add(next);
    }

    if (jsonNodeList.isEmpty()) {
      return TableColumns.of();
    }

    TableColumns of = TableColumns.of();
    for (JsonNode jsonNode : jsonNodeList) {
      of.addTableColumn(JacksonUtils.toObj(jsonNode.toString(), typeReference));
    }

    return of;
  }

  @Override
  public TableColumns getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return TableColumns.of();
  }

}
