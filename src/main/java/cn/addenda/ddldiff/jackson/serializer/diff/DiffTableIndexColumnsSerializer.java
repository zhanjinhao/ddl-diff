package cn.addenda.ddldiff.jackson.serializer.diff;

import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumn;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumns;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author addenda
 * @since 2021/9/13
 */
public class DiffTableIndexColumnsSerializer extends JsonSerializer<DiffTableIndexColumns> {

  @Override
  public void serialize(DiffTableIndexColumns value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    if (value == null) {
      jgen.writeString((String) null);
      return;
    }

    Map<String, String> jsonProperties = new HashMap<>();

    StringBuilder source = new StringBuilder();
    StringBuilder target = new StringBuilder();

    for (DiffTableIndexColumn next : value.getDiffTableIndexColumnList()) {
      String diffNameSource = next.getDiffName().getSource();
      String diffOrderSource = next.getDiffOrder().getSource();
      if (diffNameSource != null) {
        if (source.length() != 0) {
          source.append(", ");
        }
        source.append(diffNameSource);
        if (diffOrderSource != null) {
          source.append(" ").append(diffOrderSource);
        }
      }

      String diffNameTarget = next.getDiffName().getTarget();
      String diffOrderTarget = next.getDiffOrder().getTarget();
      if (diffNameTarget != null) {
        if (target.length() != 0) {
          target.append(", ");
        }
        target.append(diffNameTarget);
        if (diffOrderTarget != null) {
          target.append(" ").append(diffOrderTarget);
        }
      }
    }
    jsonProperties.put(EnvContext.getSourceName(), source.length() == 0 ? null : source.toString());
    jsonProperties.put(EnvContext.getTargetName(), target.length() == 0 ? null : target.toString());
    jgen.writeObject(jsonProperties);
  }

}
