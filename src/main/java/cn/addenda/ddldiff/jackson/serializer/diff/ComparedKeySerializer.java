package cn.addenda.ddldiff.jackson.serializer.diff;

import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.diff.ComparedKey;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author addenda
 * @since 2021/9/13
 */
public class ComparedKeySerializer extends JsonSerializer<ComparedKey> {

  @Override
  public void serialize(ComparedKey value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    if (value == null) {
      jgen.writeString((String) null);
      return;
    }
    jgen.writeString(value.getSelf() + "(" + EnvContext.getSourceName() + ")-" + value.getThat() + "(" + EnvContext.getTargetName() + ")");
  }

}
