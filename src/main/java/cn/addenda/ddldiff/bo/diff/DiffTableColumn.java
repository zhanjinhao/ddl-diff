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
        value = {"columnName",
                "diffName", "diffType",
                "diffCharset", "diffCollate",
                "diffDefaultValue", "diffIfNullable",
                "diffIfAutoIncrement", "diffComment"})
public class DiffTableColumn implements Diff {

  private static final DiffTableColumn NULL = new DiffTableColumn();

  @JsonProperty(value = "columnName")
  private ComparedKey comparedKey;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueName diffName;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueType diffType;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffCharset;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffCollate;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffDefaultValue;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueBoolean diffIfNullable;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueBoolean diffIfAutoIncrement;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueComment diffComment;

  private DiffTableColumn() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffTableColumn of(ComparedKey comparedKey, DiffValueName diffName, DiffValueType diffType,
                                   DiffValueString diffCharset, DiffValueString diffCollate,
                                   DiffValueString diffDefaultValue, DiffValueBoolean diffIfNullable,
                                   DiffValueBoolean diffIfAutoIncrement, DiffValueComment diffComment) {
    if (DiffValueName.ifNull(diffName) && DiffValueType.ifNull(diffType)
            && DiffValueString.ifNull(diffCharset) && DiffValueString.ifNull(diffCollate)
            && DiffValueString.ifNull(diffDefaultValue) && DiffValueBoolean.ifNull(diffIfNullable)
            && DiffValueBoolean.ifNull(diffIfAutoIncrement) && DiffValueComment.ifNull(diffComment)) {
      return NULL;
    }
    DiffTableColumn diffTableColumn = new DiffTableColumn();
    diffTableColumn.setComparedKey(comparedKey);
    diffTableColumn.setDiffName(diffName);
    diffTableColumn.setDiffType(diffType);
    diffTableColumn.setDiffCharset(diffCharset);
    diffTableColumn.setDiffCollate(diffCollate);
    diffTableColumn.setDiffDefaultValue(diffDefaultValue);
    diffTableColumn.setDiffIfNullable(diffIfNullable);
    diffTableColumn.setDiffIfAutoIncrement(diffIfAutoIncrement);
    diffTableColumn.setDiffComment(diffComment);
    return diffTableColumn;
  }

  public static boolean ifNull(DiffTableColumn diffTableColumn) {
    return (null == diffTableColumn) || (NULL == diffTableColumn) || NULL.equals(diffTableColumn);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffTableColumn that = (DiffTableColumn) o;
    return Objects.equals(getDiffName(), that.getDiffName())
            && Objects.equals(getDiffType(), that.getDiffType())
            && Objects.equals(getDiffCharset(), that.getDiffCharset())
            && Objects.equals(getDiffCollate(), that.getDiffCollate())
            && Objects.equals(getDiffDefaultValue(), that.getDiffDefaultValue())
            && Objects.equals(getDiffIfNullable(), that.getDiffIfNullable())
            && Objects.equals(getDiffIfAutoIncrement(), that.getDiffIfAutoIncrement())
            && Objects.equals(getDiffComment(), that.getDiffComment());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDiffName(), getDiffType(),
            getDiffCharset(), getDiffCollate(),
            getDiffDefaultValue(), getDiffIfNullable(),
            getDiffIfAutoIncrement(), getDiffComment());
  }

  @Override
  public String toString() {
    return diff();
  }

}
