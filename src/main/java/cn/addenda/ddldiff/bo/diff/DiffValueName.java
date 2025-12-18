package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffValueNameDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffValueNameSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonSerialize(using = DiffValueNameSerializer.class)
@JsonDeserialize(using = DiffValueNameDeserializer.class)
public class DiffValueName implements Diff {

  public static final DiffValueName NULL = new DiffValueName();

  private String source;

  private String target;

  private DiffValueName() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffValueName of(String source, String target) {
    // 这里为什么不判断source和target是否相等。因为：相等取决于是按runtime还是absolutely对比。
    if (source == null && target == null) {
      return NULL;
    }
    DiffValueName diffValueName = new DiffValueName();
    diffValueName.setSource(source);
    diffValueName.setTarget(target);
    return diffValueName;
  }

  public static boolean ifNull(DiffValueName diffValueName) {
    return (null == diffValueName) || (NULL == diffValueName) || NULL.equals(diffValueName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffValueName that = (DiffValueName) o;
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
