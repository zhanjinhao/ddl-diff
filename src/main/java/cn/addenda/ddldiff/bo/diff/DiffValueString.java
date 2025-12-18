package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffValueStringDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffValueStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonSerialize(using = DiffValueStringSerializer.class)
@JsonDeserialize(using = DiffValueStringDeserializer.class)
public class DiffValueString implements Diff {

  public static final DiffValueString NULL = new DiffValueString();

  private String source;

  private String target;

  private DiffValueString() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffValueString of(String source, String target) {
    // 这里为什么不判断source和target是否相等。因为：相等取决于是按runtime还是absolutely对比。
    if (source == null && target == null) {
      return NULL;
    }
    DiffValueString diffValueString = new DiffValueString();
    diffValueString.setSource(source);
    diffValueString.setTarget(target);
    return diffValueString;
  }

  public static boolean ifNull(DiffValueString diffValueString) {
    return (null == diffValueString) || (NULL == diffValueString) || NULL.equals(diffValueString);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffValueString that = (DiffValueString) o;
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
