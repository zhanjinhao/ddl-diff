package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffValueBoolean;
import cn.addenda.ddldiff.jackson.deserializer.ValueBooleanDeserializer;
import cn.addenda.ddldiff.jackson.serializer.ValueBooleanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.Objects;

@Getter
@JsonSerialize(using = ValueBooleanSerializer.class)
@JsonDeserialize(using = ValueBooleanDeserializer.class)
public class ValueBoolean implements DiffAble<ValueBoolean, DiffValueBoolean> {

  public static final ValueBoolean TRUE = ValueBoolean.of(true);
  public static final ValueBoolean FALSE = ValueBoolean.of(false);

  private boolean value;

  private ValueBoolean() {
  }

  @Override
  public ValueBoolean deepClone() {
    return this;
  }

  @Override
  public boolean absolutelyEquals(ValueBoolean that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(ValueBoolean that) {
    return equals(that);
  }

  @Override
  public DiffValueBoolean absolutelyDiff(ValueBoolean that) {
    if (that == null) {
      throw new IllegalArgumentException("cannot diff with null");
    }
    return absolutelyEquals(that) ? DiffValueBoolean.NULL : DiffValueBoolean.of(this.isValue(), that.isValue());
  }

  @Override
  public DiffValueBoolean runtimeDiff(ValueBoolean that) {
    if (that == null) {
      throw new IllegalArgumentException("cannot diff with null");
    }
    return runtimeEquals(that) ? DiffValueBoolean.NULL : DiffValueBoolean.of(this.isValue(), that.isValue());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValueBoolean valueBoolean = (ValueBoolean) o;
    return Objects.equals(value, valueBoolean.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  private static ValueBoolean of(boolean value) {
    ValueBoolean valueBoolean = new ValueBoolean();
    valueBoolean.value = value;
    return valueBoolean;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
