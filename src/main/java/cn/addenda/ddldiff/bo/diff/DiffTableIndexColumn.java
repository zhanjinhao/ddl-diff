package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class DiffTableIndexColumn implements Diff {

  private static final DiffTableIndexColumn NULL = new DiffTableIndexColumn();

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueName diffName;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueOrder diffOrder;

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
    diffTableIndexColumn.setDiffName(diffName);
    diffTableIndexColumn.setDiffOrder(diffOrder);
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
