package cn.addenda.ddldiff.jackson.serializer.diff;

import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.diff.DiffValueBoolean;
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
public class DiffValueBooleanSerializer extends JsonSerializer<DiffValueBoolean> {

  @Override
  public void serialize(DiffValueBoolean value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    if (value == null) {
      jgen.writeString((String) null);
      return;
    }

    Map<String, Boolean> jsonProperties = new HashMap<>();
    jsonProperties.put(EnvContext.getSourceName(), value.isSource());
    jsonProperties.put(EnvContext.getTargetName(), value.isTarget());
    jgen.writeObject(jsonProperties);
  }

}
