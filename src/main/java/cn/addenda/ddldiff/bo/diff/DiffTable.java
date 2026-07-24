package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffTableDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
@JsonDeserialize(using = DiffTableDeserializer.class)
public class DiffTable implements Diff {

  public static final DiffTable NULL = new DiffTable();

  @JsonProperty(value = "tableName")
  private ComparedKey comparedKey;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueName diffName = DiffValueName.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffTableColumns diffTableColumns = DiffTableColumns.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffTableIndexes diffTableIndexes = DiffTableIndexes.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffEngine = DiffValueString.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffCharset = DiffValueString.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffCollate = DiffValueString.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueComment diffComment = DiffValueComment.NULL;

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
    if (comparedKey != null) {
      diffTable.setComparedKey(comparedKey);
    }
    if (diffName != null) {
      diffTable.setDiffName(diffName);
    }
    if (diffTableColumns != null) {
      diffTable.setDiffTableColumns(diffTableColumns);
    }
    if (diffTableIndexes != null) {
      diffTable.setDiffTableIndexes(diffTableIndexes);
    }
    if (diffEngine != null) {
      diffTable.setDiffEngine(diffEngine);
    }
    if (diffCharset != null) {
      diffTable.setDiffCharset(diffCharset);
    }
    if (diffCollate != null) {
      diffTable.setDiffCollate(diffCollate);
    }
    if (diffComment != null) {
      diffTable.setDiffComment(diffComment);
    }
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
