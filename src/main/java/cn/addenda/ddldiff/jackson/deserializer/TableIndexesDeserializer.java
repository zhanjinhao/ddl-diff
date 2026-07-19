package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.TableIndex;
import cn.addenda.ddldiff.bo.TableIndexes;
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

public class TableIndexesDeserializer extends JsonDeserializer<TableIndexes> {

  private static final TypeReference<TableIndex> typeReference = new TypeReference<TableIndex>() {
  };

  @Override
  public TableIndexes deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode tree = jp.getCodec().readTree(jp);
    if (!tree.isArray()) {
      return TableIndexes.of();
    }
    ArrayNode arrayNode = (ArrayNode) tree;

    List<JsonNode> jsonNodeList = new ArrayList<>();
    for (JsonNode next : arrayNode) {
      jsonNodeList.add(next);
    }

    if (jsonNodeList.isEmpty()) {
      return TableIndexes.of();
    }

    TableIndexes of = TableIndexes.of();
    for (JsonNode jsonNode : jsonNodeList) {
      of.addTableIndex(JacksonUtils.toObj(jsonNode.toString(), typeReference));
    }

    return of;
  }


  @Override
  public TableIndexes getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return TableIndexes.of();
  }

}
