package cn.addenda.ddldiff.bo.diff;

import cn.addenda.ddldiff.bo.ValueName;
import cn.addenda.ddldiff.jackson.deserializer.diff.ComparedKeyDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.ComparedKeySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonSerialize(using = ComparedKeySerializer.class)
@JsonDeserialize(using = ComparedKeyDeserializer.class)
public class ComparedKey {

  private String self;
  private String that;

  private ComparedKey(String self, String that) {
    this.self = self;
    this.that = that;
  }

  public static ComparedKey of(String key, String that) {
    return new ComparedKey(key, that);
  }

  public static ComparedKey of(ValueName key, ValueName that) {
    return new ComparedKey(key.getValue(), that.getValue());
  }

}
