package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffValueName;
import cn.addenda.ddldiff.jackson.deserializer.ValueNameDeserializer;
import cn.addenda.ddldiff.jackson.serializer.ValueNameSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.Locale;
import java.util.Objects;

@Getter
@JsonSerialize(using = ValueNameSerializer.class)
@JsonDeserialize(using = ValueNameDeserializer.class)
public class ValueName implements DiffAble<ValueName, DiffValueName> {

  private String value;

  private ValueName() {
  }

  @Override
  public ValueName deepClone() {
    return ValueName.of(value);
  }

  @Override
  public boolean absolutelyEquals(ValueName that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(ValueName o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return Objects.equals(formatName(value), formatName(o.value));
  }

  @Override
  public DiffValueName absolutelyDiff(ValueName that) {
    if (that == null) {
      that = of();
    }
    return absolutelyEquals(that) ? DiffValueName.NULL : DiffValueName.of(this.getValue(), that.getValue());
  }

  @Override
  public DiffValueName runtimeDiff(ValueName that) {
    if (that == null) {
      that = of();
    }
    return runtimeEquals(that) ? DiffValueName.NULL : DiffValueName.of(this.getValue(), that.getValue());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValueName valueName = (ValueName) o;
    return Objects.equals(value, valueName.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  public static ValueName of(String value) {
    ValueName valueName = new ValueName();
    if (value != null) {
      valueName.value = value;
    }
    return valueName;
  }

  public static ValueName of() {
    return new ValueName();
  }

  private String formatName(String name) {
    if (name == null) {
      return null;
    }
    int len = name.length();
    int st = 0;

    while ((st < len) && (name.charAt(st) <= ' ' || name.charAt(st) == '`')) {
      st++;
    }
    while ((st < len) && (name.charAt(len - 1) <= ' ' || name.charAt(len - 1) == '`')) {
      len--;
    }
    return ((st > 0) || (len < name.length())) ?
            name.substring(st, len).toLowerCase(Locale.ROOT) : name.toLowerCase(Locale.ROOT);
  }

  @Override
  public String toString() {
    return value;
  }
}
