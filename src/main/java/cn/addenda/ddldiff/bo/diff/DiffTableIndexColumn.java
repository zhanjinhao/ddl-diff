package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffTableIndexColumnDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@JsonDeserialize(using = DiffTableIndexColumnDeserializer.class)
public class DiffTableIndexColumn implements Diff {

  public static final DiffTableIndexColumn NULL = new DiffTableIndexColumn();

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueName diffName = DiffValueName.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueOrder diffOrder = DiffValueOrder.NULL;

  private DiffTableIndexColumn() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffTableIndexColumn of(DiffValueName diffName, DiffValueOrder diffOrder) {
    if (DiffValueName.ifNull(diffName) && DiffValueOrder.ifNull(diffOrder)) {
      return NULL;
    }
    DiffTableIndexColumn diffTableIndexColumn = new DiffTableIndexColumn();
    if (diffName != null) {
      diffTableIndexColumn.setDiffName(diffName);
    }
    if (diffOrder != null) {
      diffTableIndexColumn.setDiffOrder(diffOrder);
    }
    return diffTableIndexColumn;
  }

  public static boolean ifNull(DiffTableIndexColumn diffTableIndexColumn) {
    return (null == diffTableIndexColumn) || (NULL == diffTableIndexColumn) || NULL.equals(diffTableIndexColumn);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffTableIndexColumn that = (DiffTableIndexColumn) o;
    return Objects.equals(getDiffName(), that.getDiffName())
            && Objects.equals(getDiffOrder(), that.getDiffOrder());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDiffName(), getDiffOrder());
  }

  @Override
  public String toString() {
    return diff();
  }

}
