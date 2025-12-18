package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffValueOrderDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffValueOrderSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonSerialize(using = DiffValueOrderSerializer.class)
@JsonDeserialize(using = DiffValueOrderDeserializer.class)
public class DiffValueOrder implements Diff {

  public static final DiffValueOrder NULL = new DiffValueOrder();

  private String source;

  private String target;

  private DiffValueOrder() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffValueOrder of(String source, String target) {
    // 这里为什么不判断source和target是否相等。因为：相等取决于是按runtime还是absolutely对比。
    if (source == null && target == null) {
      return NULL;
    }
    DiffValueOrder diffTableString = new DiffValueOrder();
    diffTableString.setSource(source);
    diffTableString.setTarget(target);
    return diffTableString;
  }

  public static boolean ifNull(DiffValueOrder diffValueOrder) {
    return (null == diffValueOrder) || (NULL == diffValueOrder) || NULL.equals(diffValueOrder);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffValueOrder that = (DiffValueOrder) o;
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
