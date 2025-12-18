package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@JsonPropertyOrder(
        value = {"tableName",
                "diffName", "diffTableColumns",
                "diffTableIndexes", "diffEngine",
                "diffCharset", "diffCollate",
                "diffComment"})
public class DiffTable implements Diff {

  private static final DiffTable NULL = new DiffTable();

  @JsonProperty(value = "tableName")
  private ComparedKey comparedKey;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueName diffName;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffTableColumns diffTableColumns;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffTableIndexes diffTableIndexes;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffEngine;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffCharset;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffCollate;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueComment diffComment;

  private DiffTable() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffTable of(ComparedKey comparedKey,
                             DiffValueName diffName, DiffTableColumns diffTableColumns,
                             DiffTableIndexes diffTableIndexes, DiffValueString diffEngine,
                             DiffValueString diffCharset, DiffValueString diffCollate,
                             DiffValueComment diffComment) {
    if (DiffValueName.ifNull(diffName) && DiffTableColumns.ifNull(diffTableColumns)
            && DiffTableIndexes.ifNull(diffTableIndexes) && DiffValueString.ifNull(diffEngine)
            && DiffValueString.ifNull(diffCharset) && DiffValueString.ifNull(diffCollate)
            && DiffValueComment.ifNull(diffComment)) {
      return NULL;
    }

    DiffTable diffTable = new DiffTable();
    diffTable.setComparedKey(comparedKey);
    diffTable.setDiffName(diffName);
    diffTable.setDiffTableColumns(diffTableColumns);
    diffTable.setDiffTableIndexes(diffTableIndexes);
    diffTable.setDiffEngine(diffEngine);
    diffTable.setDiffCharset(diffCharset);
    diffTable.setDiffCollate(diffCollate);
    diffTable.setDiffComment(diffComment);
    return diffTable;
  }

  public static boolean ifNull(DiffTable diffTable) {
    return (null == diffTable) || (NULL == diffTable) || NULL.equals(diffTable);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffTable diffTable = (DiffTable) o;
    return Objects.equals(getDiffName(), diffTable.getDiffName())
            && Objects.equals(getDiffTableColumns(), diffTable.getDiffTableColumns())
            && Objects.equals(getDiffTableIndexes(), diffTable.getDiffTableIndexes())
            && Objects.equals(getDiffEngine(), diffTable.getDiffEngine())
            && Objects.equals(getDiffCharset(), diffTable.getDiffCharset())
            && Objects.equals(getDiffCollate(), diffTable.getDiffCollate())
            && Objects.equals(getDiffComment(), diffTable.getDiffComment());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDiffName(), getDiffTableColumns(),
            getDiffTableIndexes(), getDiffEngine(),
            getDiffCharset(), getDiffCollate(), getDiffComment());
  }

  @Override
  public String toString() {
    return diff();
  }

}
