package cn.addenda.ddldiff.jackson.serializer;

import cn.addenda.ddldiff.bo.ValueName;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author addenda
 * @since 2021/9/13
 */
public class ValueNameSerializer extends JsonSerializer<ValueName> {

  @Override
  public void serialize(ValueName value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    if (value == null) {
      jgen.writeString((String) null);
      return;
    }
    jgen.writeString(value.getValue());
  }

}
