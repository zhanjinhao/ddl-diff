package cn.addenda.ddldiff.jackson.serializer;

import cn.addenda.ddldiff.bo.ValueBoolean;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author addenda
 * @since 2021/9/13
 */
public class ValueBooleanSerializer extends JsonSerializer<ValueBoolean> {

  @Override
  public void serialize(ValueBoolean value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    if (value == null) {
      jgen.writeString((String) null);
      return;
    }
    jgen.writeBoolean(value.isValue());
  }

}
