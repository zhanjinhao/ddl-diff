package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffTableColumnsDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffTableColumnsSerializer;
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
@JsonSerialize(using = DiffTableColumnsSerializer.class)
@JsonDeserialize(using = DiffTableColumnsDeserializer.class)
public class DiffTableColumns implements Diff, Iterable<DiffTableColumn> {

  public static final DiffTableColumns NULL = new DiffTableColumns();

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private List<DiffTableColumn> diffTableColumnList = new ArrayList<>();

  private DiffTableColumns() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffTableColumns of(List<DiffTableColumn> diffTableColumnList) {
    if (diffTableColumnList == null || diffTableColumnList.isEmpty()) {
      return NULL;
    }

    DiffTableColumns diffTableColumns = new DiffTableColumns();
    diffTableColumns.diffTableColumnList.addAll(diffTableColumnList);
    return diffTableColumns;
  }

  public static boolean ifNull(DiffTableColumns diffTableColumns) {
    return (null == diffTableColumns) || (NULL == diffTableColumns) || NULL.equals(diffTableColumns);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffTableColumns that = (DiffTableColumns) o;
    return Objects.equals(diffTableColumnList, that.diffTableColumnList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(diffTableColumnList.toArray(new Object[0]));
  }

  @Override
  public String toString() {
    return diff();
  }

  @Override
  public Iterator<DiffTableColumn> iterator() {
    return diffTableColumnList.iterator();
  }

}
