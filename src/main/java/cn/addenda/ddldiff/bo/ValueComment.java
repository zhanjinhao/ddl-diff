package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffValueComment;
import cn.addenda.ddldiff.jackson.deserializer.ValueCommentDeserializer;
import cn.addenda.ddldiff.jackson.serializer.ValueCommentSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.Objects;

@Getter
@JsonSerialize(using = ValueCommentSerializer.class)
@JsonDeserialize(using = ValueCommentDeserializer.class)
public class ValueComment implements DiffAble<ValueComment, DiffValueComment> {

  private String value;

  private ValueComment() {
  }

  @Override
  public ValueComment deepClone() {
    return ValueComment.of(value);
  }

  @Override
  public boolean absolutelyEquals(ValueComment that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(ValueComment that) {
    return true;
  }

  @Override
  public DiffValueComment absolutelyDiff(ValueComment that) {
    if (that == null) {
      that = of();
    }
    return absolutelyEquals(that) ? DiffValueComment.NULL : DiffValueComment.of(this.getValue(), that.getValue());
  }

  @Override
  public DiffValueComment runtimeDiff(ValueComment that) {
    return DiffValueComment.NULL;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValueComment valueString = (ValueComment) o;
    return Objects.equals(value, valueString.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  public static ValueComment of(String value) {
    ValueComment valueString = new ValueComment();
    if (value != null) {
      valueString.value = value;
    }
    return valueString;
  }

  public static ValueComment of() {
    return new ValueComment();
  }

  @Override
  public String toString() {
    return value;
  }
}
