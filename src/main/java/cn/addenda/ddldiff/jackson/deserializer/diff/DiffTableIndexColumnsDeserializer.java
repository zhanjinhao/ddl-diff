package cn.addenda.ddldiff.jackson.deserializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.ValueName;
import cn.addenda.ddldiff.bo.ValueOrder;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumn;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumns;
import cn.addenda.ddldiff.bo.diff.DiffValueName;
import cn.addenda.ddldiff.bo.diff.DiffValueOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DiffTableIndexColumnsDeserializer extends JsonDeserializer<DiffTableIndexColumns> {

  private static final TypeReference<LinkedHashMap<String, String>> typeReference = new TypeReference<LinkedHashMap<String, String>>() {
  };

  @Override
  public DiffTableIndexColumns deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode jsonNode = jp.getCodec().readTree(jp);
    final String s = jsonNode.toString();
    if (s == null || s.isEmpty() || "null".equals(s)) {
      return DiffTableIndexColumns.NULL;
    }

    List<DiffTableIndexColumn> diffTableIndexColumnList = new ArrayList<>();

    LinkedHashMap<String, String> jsonProperties = JacksonUtils.toObj(s, typeReference);

    String source = jsonProperties.get(EnvContext.getSourceName());
    String target = jsonProperties.get(EnvContext.getTargetName());

    List<String[]> sourcePieceList = source == null ? new ArrayList<>() : Arrays.stream(source.split(","))
            .map(a -> a.trim().split("\\s+"))
            .collect(Collectors.toList());
    List<String[]> targetPieceList = target == null ? new ArrayList<>() : Arrays.stream(target.split(","))
            .map(a -> a.trim().split("\\s+"))
            .collect(Collectors.toList());

    Iterator<String[]> sourceIterator = sourcePieceList.iterator();
    Iterator<String[]> targetIterator = targetPieceList.iterator();
    while (sourceIterator.hasNext() && targetIterator.hasNext()) {
      String[] sourceNext = sourceIterator.next();
      String[] targetNext = targetIterator.next();
      DiffValueName diffValueName = DiffValueName.of(sourceNext[0], targetNext[0]);
      DiffValueOrder diffValueOrder;
      if (sourceNext.length > 1 && targetNext.length > 1) {
        diffValueOrder = DiffValueOrder.of(sourceNext[1], targetNext[1]);
      } else if (sourceNext.length > 1 && targetNext.length == 1) {
        diffValueOrder = DiffValueOrder.of(sourceNext[1], ValueOrder.of().getValue());
      } else if (sourceNext.length == 1 && targetNext.length > 1) {
        diffValueOrder = DiffValueOrder.of(ValueOrder.of().getValue(), targetNext[1]);
      } else if (sourceNext.length == 1 && targetNext.length == 1) {
        diffValueOrder = DiffValueOrder.of(ValueOrder.of().getValue(), ValueOrder.of().getValue());
      } else {
        throw new IllegalArgumentException(
                String.format("Unsupported table index column, source: %s, target: %s.", source, target));
      }
      diffTableIndexColumnList.add(DiffTableIndexColumn.of(diffValueName, diffValueOrder));
    }

    while (sourceIterator.hasNext()) {
      String[] sourceNext = sourceIterator.next();
      DiffValueName diffValueName = DiffValueName.of(sourceNext[0], ValueName.of().getValue());
      DiffValueOrder diffValueOrder;
      if (sourceNext.length > 1) {
        diffValueOrder = DiffValueOrder.of(sourceNext[1], ValueOrder.of().getValue());
      } else {
        diffValueOrder = DiffValueOrder.of(ValueOrder.of().getValue(), ValueOrder.of().getValue());
      }
      diffTableIndexColumnList.add(DiffTableIndexColumn.of(diffValueName, diffValueOrder));
    }

    while (targetIterator.hasNext()) {
      String[] targetNext = targetIterator.next();
      DiffValueName diffValueName = DiffValueName.of(ValueName.of().getValue(), targetNext[0]);
      DiffValueOrder diffValueOrder;
      if (targetNext.length > 1) {
        diffValueOrder = DiffValueOrder.of(ValueOrder.of().getValue(), targetNext[1]);
      } else {
        diffValueOrder = DiffValueOrder.of(ValueOrder.of().getValue(), ValueOrder.of().getValue());
      }
      diffTableIndexColumnList.add(DiffTableIndexColumn.of(diffValueName, diffValueOrder));
    }

    return DiffTableIndexColumns.of(diffTableIndexColumnList);
  }

  @Override
  public DiffTableIndexColumns getNullValue(DeserializationContext ctxt) throws JsonMappingException {
    return DiffTableIndexColumns.NULL;
  }

}
