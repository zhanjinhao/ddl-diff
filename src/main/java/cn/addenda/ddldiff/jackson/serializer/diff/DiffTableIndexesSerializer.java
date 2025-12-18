package cn.addenda.ddldiff.jackson.serializer.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexes;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DiffTableIndexesSerializer extends JsonSerializer<DiffTableIndexes> {

  @Override
  public void serialize(DiffTableIndexes value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
    if (value == null) {
      jgen.writeString((String) null);
      return;
    }

    jgen.writeRawValue(JacksonUtils.toStr(value.getDiffTableIndexList()));
  }

}
