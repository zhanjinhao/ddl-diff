package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffTableIndexColumnsDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffTableIndexColumnsSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@JsonSerialize(using = DiffTableIndexColumnsSerializer.class)
@JsonDeserialize(using = DiffTableIndexColumnsDeserializer.class)
public class DiffTableIndexColumns implements Diff, Iterable<DiffTableIndexColumn> {

  public static final DiffTableIndexColumns NULL = new DiffTableIndexColumns();

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private List<DiffTableIndexColumn> diffTableIndexColumnList = new ArrayList<>();

  private DiffTableIndexColumns() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffTableIndexColumns of(List<DiffTableIndexColumn> diffTableIndexColumnList) {
    if (diffTableIndexColumnList == null || diffTableIndexColumnList.isEmpty()) {
      return NULL;
    }
    DiffTableIndexColumns diffTableIndexColumns = new DiffTableIndexColumns();
    diffTableIndexColumns.diffTableIndexColumnList.addAll(diffTableIndexColumnList);
    return diffTableIndexColumns;
  }

  public static boolean ifNull(DiffTableIndexColumns diffTableIndexColumns) {
    return (null == diffTableIndexColumns) || (NULL == diffTableIndexColumns) || NULL.equals(diffTableIndexColumns);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffTableIndexColumns that = (DiffTableIndexColumns) o;
    return Objects.equals(diffTableIndexColumnList, that.diffTableIndexColumnList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(diffTableIndexColumnList.toArray(new Object[0]));
  }

  @Override
  public String toString() {
    return diff();
  }

  @Override
  public Iterator<DiffTableIndexColumn> iterator() {
    return diffTableIndexColumnList.iterator();
  }

}
