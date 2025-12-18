package cn.addenda.ddldiff.jackson.serializer.diff;

import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.diff.DiffValueComment;
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
public class DiffValueCommentSerializer extends JsonSerializer<DiffValueComment> {

  @Override
  public void serialize(DiffValueComment value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    if (value == null) {
      jgen.writeString((String) null);
      return;
    }

    Map<String, String> jsonProperties = new HashMap<>();
    jsonProperties.put(EnvContext.getSourceName(), value.getSource());
    jsonProperties.put(EnvContext.getTargetName(), value.getTarget());
    jgen.writeObject(jsonProperties);
  }

}
