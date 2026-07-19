package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffValueString;
import cn.addenda.ddldiff.jackson.deserializer.ValueStringDeserializer;
import cn.addenda.ddldiff.jackson.serializer.ValueStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.Objects;

@Getter
@JsonSerialize(using = ValueStringSerializer.class)
@JsonDeserialize(using = ValueStringDeserializer.class)
public class ValueString implements DiffAble<ValueString, DiffValueString> {

  private String value;

  private ValueString() {
  }

  @Override
  public ValueString deepClone() {
    return ValueString.of(value);
  }

  @Override
  public boolean absolutelyEquals(ValueString that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(ValueString that) {
    if (that == null) {
      that = of();
    }
    return equals(that);
  }

  @Override
  public DiffValueString absolutelyDiff(ValueString that) {
    if (that == null) {
      that = of();
    }
    return absolutelyEquals(that) ? DiffValueString.NULL : DiffValueString.of(this.getValue(), that.getValue());
  }

  @Override
  public DiffValueString runtimeDiff(ValueString that) {
    if (that == null) {
      that = of();
    }
    return runtimeEquals(that) ? DiffValueString.NULL : DiffValueString.of(this.getValue(), that.getValue());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValueString valueString = (ValueString) o;
    return Objects.equals(value, valueString.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  public static ValueString of(String value) {
    ValueString valueString = new ValueString();
    if (value != null) {
      valueString.value = value;
    }
    return valueString;
  }

  public static ValueString of() {
    return new ValueString();
  }

  @Override
  public String toString() {
    return value;
  }
}
