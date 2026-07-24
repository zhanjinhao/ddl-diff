package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffTableIndexDeserializer;
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
        value = {"indexName",
                "diffTableIndexColumns", "diffName",
                "diffIndexType", "diffComment"})
@JsonDeserialize(using = DiffTableIndexDeserializer.class)
public class DiffTableIndex implements Diff {

  public static final DiffTableIndex NULL = new DiffTableIndex();

  @JsonProperty(value = "indexName")
  private ComparedKey comparedKey;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffTableIndexColumns diffTableIndexColumns = DiffTableIndexColumns.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueName diffName = DiffValueName.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffTableIndexType diffIndexType = DiffTableIndexType.NULL;

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private DiffValueComment diffComment = DiffValueComment.NULL;

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
    DiffTableIndex diffTableIndex = new DiffTableIndex();
    if (comparedKey != null) {
      diffTableIndex.setComparedKey(comparedKey);
    }
    if (diffTableIndexColumns != null) {
      diffTableIndex.setDiffTableIndexColumns(diffTableIndexColumns);
    }
    if (diffName != null) {
      diffTableIndex.setDiffName(diffName);
    }
    if (diffIndexType != null) {
      diffTableIndex.setDiffIndexType(diffIndexType);
    }
    if (diffComment != null) {
      diffTableIndex.setDiffComment(diffComment);
    }
    return diffTableIndex;
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
