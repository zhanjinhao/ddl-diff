package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffValueOrder;
import cn.addenda.ddldiff.jackson.deserializer.ValueOrderDeserializer;
import cn.addenda.ddldiff.jackson.serializer.ValueOrderSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.Locale;
import java.util.Objects;

@Getter
@JsonSerialize(using = ValueOrderSerializer.class)
@JsonDeserialize(using = ValueOrderDeserializer.class)
public class ValueOrder implements DiffAble<ValueOrder, DiffValueOrder> {

  private String value;

  private ValueOrder() {
  }

  @Override
  public ValueOrder deepClone() {
    return ValueOrder.of(value);
  }

  @Override
  public boolean absolutelyEquals(ValueOrder that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(ValueOrder o) {
    if (this == o) return true;
    if (o == null) {
      o = of();
    }
    if (getClass() != o.getClass()) return false;
    return Objects.equals(formatOrder(value), formatOrder(o.value));
  }

  @Override
  public DiffValueOrder absolutelyDiff(ValueOrder that) {
    if (that == null) {
      that = of();
    }
    return absolutelyEquals(that) ? DiffValueOrder.NULL : DiffValueOrder.of(this.getValue(), that.getValue());
  }

  @Override
  public DiffValueOrder runtimeDiff(ValueOrder that) {
    if (that == null) {
      that = of();
    }
    return runtimeEquals(that) ? DiffValueOrder.NULL : DiffValueOrder.of(this.getValue(), that.getValue());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValueOrder valueOrder = (ValueOrder) o;
    return Objects.equals(value, valueOrder.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  public static ValueOrder of(String value) {
    ValueOrder valueOrder = new ValueOrder();
    if (value != null) {
      valueOrder.value = value;
    }
    return valueOrder;
  }

  public static ValueOrder of() {
    return new ValueOrder();
  }

  private String formatOrder(String order) {
    if (order == null) {
      return "asc";
    }
    int len = order.length();
    int st = 0;

    while ((st < len) && (order.charAt(st) <= ' ')) {
      st++;
    }
    while ((st < len) && (order.charAt(len - 1) <= ' ')) {
      len--;
    }
    return ((st > 0) || (len < order.length())) ?
            order.substring(st, len).toLowerCase(Locale.ROOT) : order.toLowerCase(Locale.ROOT);
  }

  @Override
  public String toString() {
    return value;
  }
}
