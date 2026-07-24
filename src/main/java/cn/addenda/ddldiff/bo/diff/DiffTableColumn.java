package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffTableColumnDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
@JsonDeserialize(using = DiffTableColumnDeserializer.class)
public class DiffTableColumn implements Diff {

  public static final DiffTableColumn NULL = new DiffTableColumn();

  @JsonProperty(value = "columnName")
  private ComparedKey comparedKey;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueName diffName = DiffValueName.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueType diffType = DiffValueType.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffCharset = DiffValueString.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffCollate = DiffValueString.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueString diffDefaultValue = DiffValueString.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueBoolean diffIfNullable = DiffValueBoolean.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueBoolean diffIfAutoIncrement = DiffValueBoolean.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueComment diffComment = DiffValueComment.NULL;

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
    if (comparedKey != null) {
      diffTableColumn.setComparedKey(comparedKey);
    }
    if (diffName != null) {
      diffTableColumn.setDiffName(diffName);
    }
    if (diffType != null) {
      diffTableColumn.setDiffType(diffType);
    }
    if (diffCharset != null) {
      diffTableColumn.setDiffCharset(diffCharset);
    }
    if (diffCollate != null) {
      diffTableColumn.setDiffCollate(diffCollate);
    }
    if (diffDefaultValue != null) {
      diffTableColumn.setDiffDefaultValue(diffDefaultValue);
    }
    if (diffIfNullable != null) {
      diffTableColumn.setDiffIfNullable(diffIfNullable);
    }
    if (diffIfAutoIncrement != null) {
      diffTableColumn.setDiffIfAutoIncrement(diffIfAutoIncrement);
    }
    if (diffComment != null) {
      diffTableColumn.setDiffComment(diffComment);
    }
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
