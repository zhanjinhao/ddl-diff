package cn.addenda.ddldiff.jackson.serializer;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.TableIndexes;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TableIndexesSerializer extends JsonSerializer<TableIndexes> {

  @Override
  public void serialize(TableIndexes value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
    if (value == null) {
      jgen.writeString((String) null);
      return;
    }

    jgen.writeRawValue(JacksonUtils.toStr(value.getTableIndexList()));
  }

}
