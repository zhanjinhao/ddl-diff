package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.diff.DiffTableIndex;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexes;
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

public class DiffTableIndexesDeserializer extends JsonDeserializer<DiffTableIndexes> {

  private static final TypeReference<DiffTableIndex> typeReference = new TypeReference<DiffTableIndex>() {
  };

  @Override
  public DiffTableIndexes deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    ArrayNode arrayNode = jp.getCodec().readTree(jp);

    List<JsonNode> jsonNodeList = new ArrayList<>();
    for (JsonNode next : arrayNode) {
      jsonNodeList.add(next);
    }

    if (jsonNodeList.isEmpty()) {
      return DiffTableIndexes.NULL;
    }

    List<DiffTableIndex> tableIndexList = new ArrayList<>();
    for (JsonNode jsonNode : jsonNodeList) {
      tableIndexList.add(JacksonUtils.toObj(jsonNode.toString(), typeReference));
    }

    return DiffTableIndexes.of(tableIndexList);
  }


  @Override
  public DiffTableIndexes getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffTableIndexes.NULL;
  }

}
