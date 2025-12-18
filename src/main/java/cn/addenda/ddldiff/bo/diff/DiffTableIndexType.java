package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.IndexType;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffTableIndexTypeDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffTableIndexTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonSerialize(using = DiffTableIndexTypeSerializer.class)
@JsonDeserialize(using = DiffTableIndexTypeDeserializer.class)
public class DiffTableIndexType implements Diff {

  public static final DiffTableIndexType NULL = new DiffTableIndexType();

  private IndexType source;

  private IndexType target;

  private DiffTableIndexType() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffTableIndexType of(IndexType source, IndexType target) {
    if (source == target) {
      return NULL;
    }
    DiffTableIndexType diffTableIndexType = new DiffTableIndexType();
    diffTableIndexType.setSource(source);
    diffTableIndexType.setTarget(target);
    return diffTableIndexType;
  }

  public static boolean ifNull(DiffTableIndexType diffTableIndexType) {
    return (null == diffTableIndexType) || (NULL == diffTableIndexType) || NULL.equals(diffTableIndexType);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffTableIndexType that = (DiffTableIndexType) o;
    return getSource() == that.getSource()
            && getTarget() == that.getTarget();
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
