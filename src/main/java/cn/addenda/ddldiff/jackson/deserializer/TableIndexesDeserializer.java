package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.TableIndex;
import cn.addenda.ddldiff.bo.TableIndexes;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableIndexesDeserializer extends AbstractDiffAbleJsonDeserializer<TableIndexes> {

  private static final TypeReference<TableIndex> typeReference = new TypeReference<TableIndex>() {
  };

  @Override
  public TableIndexes deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (!jsonNode.isArray()) {
      if (ifNull(jsonNode)) {
        return TableIndexes.of();
      } else {
        throw from(jp, jsonNode, TableIndexes.class);
      }
    }
    ArrayNode arrayNode = (ArrayNode) jsonNode;

    List<JsonNode> jsonNodeList = new ArrayList<>();
    for (JsonNode next : arrayNode) {
      jsonNodeList.add(next);
    }

    if (jsonNodeList.isEmpty()) {
      return TableIndexes.of();
    }

    TableIndexes of = TableIndexes.of();
    for (JsonNode node : jsonNodeList) {
      of.addTableIndex(JacksonUtils.toObj(node.toString(), typeReference));
    }

    return of;
  }


  @Override
  public TableIndexes getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return TableIndexes.of();
  }

}
