package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffValueCommentDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffValueCommentSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonSerialize(using = DiffValueCommentSerializer.class)
@JsonDeserialize(using = DiffValueCommentDeserializer.class)
public class DiffValueComment implements Diff {

  public static final DiffValueComment NULL = new DiffValueComment();

  private String source;

  private String target;

  private DiffValueComment() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffValueComment of(String source, String target) {
    // 这里为什么不判断source和target是否相等。因为：相等取决于是按runtime还是absolutely对比。
    if (source == null && target == null) {
      return NULL;
    }
    DiffValueComment diffValueComment = new DiffValueComment();
    diffValueComment.setSource(source);
    diffValueComment.setTarget(target);
    return diffValueComment;
  }

  public static boolean ifNull(DiffValueComment diffValueComment) {
    return (null == diffValueComment) || (NULL == diffValueComment) || NULL.equals(diffValueComment);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffValueComment that = (DiffValueComment) o;
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
