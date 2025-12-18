package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffValueTypeDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffValueTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@JsonDeserialize(using = DiffValueTypeDeserializer.class)
@JsonSerialize(using = DiffValueTypeSerializer.class)
public class DiffValueType implements Diff {

  public static final DiffValueType NULL = new DiffValueType();

  private String source;

  private String target;

  private DiffValueType() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffValueType of(String source, String target) {
    // 这里为什么不判断source和target是否相等。因为：相等取决于是按runtime还是absolutely对比。
    if (source == null && target == null) {
      return NULL;
    }
    DiffValueType diffValueType = new DiffValueType();
    diffValueType.setSource(source);
    diffValueType.setTarget(target);
    return diffValueType;
  }

  public static boolean ifNull(DiffValueType diffValueType) {
    return (null == diffValueType) || (NULL == diffValueType) || NULL.equals(diffValueType);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffValueType that = (DiffValueType) o;
    return Objects.equals(getSource(), that.getSource())
            && Objects.equals(getTarget(), that.getTarget());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSource(), getTarget());
  }

  @Override
  public String toString() {
    return diff();
  }

}
