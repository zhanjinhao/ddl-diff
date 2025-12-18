package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonPropertyOrder(
        value = {"indexName",
                "diffTableIndexColumns", "diffName",
                "diffIndexType", "diffComment"})
public class DiffTableIndex implements Diff {

  private static final DiffTableIndex NULL = new DiffTableIndex();

  @JsonProperty(value = "indexName")
  private ComparedKey comparedKey;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffTableIndexColumns diffTableIndexColumns;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueName diffName;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffTableIndexType diffIndexType;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueComment diffComment;

  private DiffTableIndex() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffTableIndex of(ComparedKey comparedKey,
                                  DiffTableIndexColumns diffTableIndexColumns, DiffValueName diffName,
                                  DiffTableIndexType diffIndexType, DiffValueComment diffComment) {
    if (DiffTableIndexColumns.ifNull(diffTableIndexColumns) && DiffValueName.ifNull(diffName)
            && DiffTableIndexType.ifNull(diffIndexType) && DiffValueComment.ifNull(diffComment)) {
      return NULL;
    }
    DiffTableIndex diffTableColumn = new DiffTableIndex();
    diffTableColumn.setComparedKey(comparedKey);
    diffTableColumn.setDiffTableIndexColumns(diffTableIndexColumns);
    diffTableColumn.setDiffName(diffName);
    diffTableColumn.setDiffIndexType(diffIndexType);
    diffTableColumn.setDiffComment(diffComment);
    return diffTableColumn;
  }

  public static boolean ifNull(DiffTableIndex diffTableIndex) {
    return (null == diffTableIndex) || (NULL == diffTableIndex) || NULL.equals(diffTableIndex);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffTableIndex that = (DiffTableIndex) o;
    return Objects.equals(getDiffTableIndexColumns(), that.getDiffTableIndexColumns())
            && Objects.equals(getDiffName(), that.getDiffName())
            && Objects.equals(getDiffIndexType(), that.getDiffIndexType())
            && Objects.equals(getDiffComment(), that.getDiffComment());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDiffTableIndexColumns(), getDiffName(), getDiffIndexType(), getDiffComment());
  }

  @Override
  public String toString() {
    return diff();
  }

}
