package cn.addenda.ddldiff.jackson.serializer;

import cn.addenda.ddldiff.bo.TableIndexColumn;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author addenda
 * @since 2021/9/13
 */
public class TableIndexColumnSerializer extends JsonSerializer<TableIndexColumn> {

  @Override
  public void serialize(TableIndexColumn value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    if (value == null) {
      jgen.writeString((String) null);
      return;
    }
    StringBuilder sb = new StringBuilder();
    String name = value.getName().getValue();
    String order = value.getOrder().getValue();
    if (name != null) {
      sb.append(name);
      if (order != null) {
        sb.append(" ").append(order);
      }
    }
    jgen.writeString(sb.toString());
  }

}
