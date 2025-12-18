package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffValueBooleanDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffValueBooleanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonSerialize(using = DiffValueBooleanSerializer.class)
@JsonDeserialize(using = DiffValueBooleanDeserializer.class)
public class DiffValueBoolean implements Diff {

  public static final DiffValueBoolean NULL = new DiffValueBoolean();

  private boolean source;

  private boolean target;

  private DiffValueBoolean() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffValueBoolean of(boolean source, boolean target) {
    // 这里为什么不判断source和target是否相等。因为：相等取决于是按runtime还是absolutely对比。
    if (source == target) {
      return NULL;
    }
    DiffValueBoolean diffValueBoolean = new DiffValueBoolean();
    diffValueBoolean.setSource(source);
    diffValueBoolean.setTarget(target);
    return diffValueBoolean;
  }

  public static boolean ifNull(DiffValueBoolean diffValueBoolean) {
    return (null == diffValueBoolean) || (NULL == diffValueBoolean) || NULL.equals(diffValueBoolean);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffValueBoolean that = (DiffValueBoolean) o;
    return isSource() == that.isSource()
            && isTarget() == that.isTarget();
  }

  @Override
  public int hashCode() {
    return Objects.hash(isSource(), isTarget());
  }

  @Override
  public String toString() {
    return diff();
  }

}
