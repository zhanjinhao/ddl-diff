package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffValueType;
import cn.addenda.ddldiff.jackson.deserializer.ValueTypeDeserializer;
import cn.addenda.ddldiff.jackson.serializer.ValueTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.Locale;
import java.util.Objects;

@Getter
@JsonSerialize(using = ValueTypeSerializer.class)
@JsonDeserialize(using = ValueTypeDeserializer.class)
public class ValueType implements DiffAble<ValueType, DiffValueType> {

  private String value;

  private ValueType() {
  }

  @Override
  public ValueType deepClone() {
    return ValueType.of(value);
  }

  @Override
  public boolean absolutelyEquals(ValueType that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(ValueType o) {
    if (this == o) return true;
    if (o == null) {
      o = of();
    }
    if (getClass() != o.getClass()) return false;
    return Objects.equals(formatType(value), formatType(o.value));
  }

  @Override
  public DiffValueType absolutelyDiff(ValueType that) {
    if (that == null) {
      that = of();
    }
    return absolutelyEquals(that) ? DiffValueType.NULL : DiffValueType.of(this.getValue(), that.getValue());
  }

  @Override
  public DiffValueType runtimeDiff(ValueType that) {
    if (that == null) {
      that = of();
    }
    return runtimeEquals(that) ? DiffValueType.NULL : DiffValueType.of(this.getValue(), that.getValue());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValueType valueType = (ValueType) o;
    return Objects.equals(value, valueType.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  public static ValueType of(String value) {
    ValueType valueType = new ValueType();
    if (value != null) {
      valueType.value = value;
    }
    return valueType;
  }

  public static ValueType of() {
    return new ValueType();
  }

  private String formatType(String type) {
    if (type == null) {
      return null;
    }
    int len = type.length();
    int st = 0;

    while ((st < len) && (type.charAt(st) <= ' ')) {
      st++;
    }
    while ((st < len) && (type.charAt(len - 1) <= ' ')) {
      len--;
    }
    return ((st > 0) || (len < type.length())) ?
            type.substring(st, len).toLowerCase(Locale.ROOT) : type.toLowerCase(Locale.ROOT);
  }

  @Override
  public String toString() {
    return value;
  }
}
