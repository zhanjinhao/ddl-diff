package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.diff.DiffTableColumn;
import cn.addenda.ddldiff.bo.diff.DiffTableColumns;
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

public class DiffTableColumnsDeserializer extends JsonDeserializer<DiffTableColumns> {

  private static final TypeReference<DiffTableColumn> typeReference = new TypeReference<DiffTableColumn>() {
  };

  @Override
  public DiffTableColumns deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    ArrayNode arrayNode = jp.getCodec().readTree(jp);

    List<JsonNode> jsonNodeList = new ArrayList<>();
    for (JsonNode next : arrayNode) {
      jsonNodeList.add(next);
    }

    if (jsonNodeList.isEmpty()) {
      return DiffTableColumns.NULL;
    }
    List<DiffTableColumn> diffTableColumnList = new ArrayList<>();
    for (JsonNode jsonNode : jsonNodeList) {
      diffTableColumnList.add(JacksonUtils.toObj(jsonNode.toString(), typeReference));
    }

    return DiffTableColumns.of(diffTableColumnList);
  }

  @Override
  public DiffTableColumns getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffTableColumns.NULL;
  }

}
