package cn.addenda.ddldiff.jackson.deserializer;

import cn.addenda.ddldiff.bo.TableIndexColumn;
import cn.addenda.ddldiff.bo.ValueName;
import cn.addenda.ddldiff.bo.ValueOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class TableIndexColumnDeserializer extends AbstractDiffAbleJsonDeserializer<TableIndexColumn> {

  @Override
  public TableIndexColumn deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    if (ifNull(jsonNode)) {
      return TableIndexColumn.of();
    } else {
      if (jsonNode.isTextual()) {
        final String s = jsonNode.asText();
        TableIndexColumn.TableIndexColumnBuilder builder = TableIndexColumn.builder();

        String[] split = s.split("\\s+");
        if (split.length > 0) {
          builder.name(ValueName.of(split[0]));
        }
        if (split.length > 1) {
          builder.order(ValueOrder.of(split[1]));
        }

        return builder.build();
      }
    }

    throw from(jp, jsonNode, TableIndexColumn.class);
  }

  @Override
  public TableIndexColumn getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return TableIndexColumn.of();
  }

}
