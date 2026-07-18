package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumn;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumns;
import cn.addenda.ddldiff.bo.diff.DiffValueName;
import cn.addenda.ddldiff.bo.diff.DiffValueOrder;
import cn.addenda.ddldiff.jackson.deserializer.TableIndexColumnsDeserializer;
import cn.addenda.ddldiff.jackson.serializer.TableIndexColumnsSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Getter
@JsonSerialize(using = TableIndexColumnsSerializer.class)
@JsonDeserialize(using = TableIndexColumnsDeserializer.class)
public class TableIndexColumns implements DiffAble<TableIndexColumns, DiffTableIndexColumns>, Iterable<TableIndexColumn> {

  private final List<TableIndexColumn> tableIndexColumnList = new ArrayList<>();

  private TableIndexColumns() {
  }

  public void addTableIndexColumn(TableIndexColumn tableIndexColumn) {
    if (tableIndexColumn == null) {
      throw new UnsupportedOperationException("can not add null.");
    }
    this.tableIndexColumnList.add(tableIndexColumn);
  }

  @Override
  public TableIndexColumns deepClone() {
    TableIndexColumns tableIndexColumns = TableIndexColumns.of();
    for (TableIndexColumn tableIndexColumn : tableIndexColumnList) {
      tableIndexColumns.addTableIndexColumn(tableIndexColumn.deepClone());
    }
    return tableIndexColumns;
  }

  @Override
  public boolean absolutelyEquals(TableIndexColumns that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(TableIndexColumns that) {
    if (that == this)
      return true;
    if (that == null) {
      that = of();
    }
    Iterator<TableIndexColumn> iterator = this.iterator();
    Iterator<TableIndexColumn> thatIterator = that.iterator();
    while (iterator.hasNext() && thatIterator.hasNext()) {
      TableIndexColumn tableIndexColumn = iterator.next();
      TableIndexColumn thatTableIndexColumn = thatIterator.next();
      if (!(tableIndexColumn == null ? thatTableIndexColumn == null : tableIndexColumn.runtimeEquals(thatTableIndexColumn)))
        return false;
    }
    return !(iterator.hasNext() || thatIterator.hasNext());
  }

  @Override
  public DiffTableIndexColumns absolutelyDiff(TableIndexColumns that) {
    if (that == null) {
      that = of();
    }
    if (absolutelyEquals(that)) {
      return DiffTableIndexColumns.of(new ArrayList<>());
    }

    return diff(that);
  }

  @Override
  public DiffTableIndexColumns runtimeDiff(TableIndexColumns that) {
    if (that == null) {
      that = of();
    }
    if (runtimeEquals(that)) {
      return DiffTableIndexColumns.of(new ArrayList<>());
    }

    return diff(that);
  }

  private DiffTableIndexColumns diff(TableIndexColumns that) {
    Iterator<TableIndexColumn> iterator = this.iterator();
    Iterator<TableIndexColumn> thatIterator = that.iterator();

    List<DiffTableIndexColumn> diffTableIndexColumnList = new ArrayList<>();

    while (iterator.hasNext() && thatIterator.hasNext()) {
      TableIndexColumn next = iterator.next();
      TableIndexColumn thatNext = thatIterator.next();
      diffTableIndexColumnList.add(DiffTableIndexColumn.of(
              DiffValueName.of(next.getName().getValue(), thatNext.getName().getValue()),
              DiffValueOrder.of(next.getOrder().getValue(), thatNext.getOrder().getValue())));
    }

    while (iterator.hasNext()) {
      TableIndexColumn next = iterator.next();
      diffTableIndexColumnList.add(DiffTableIndexColumn.of(
              DiffValueName.of(next.getName().getValue(), ValueName.of().getValue()),
              DiffValueOrder.of(next.getOrder().getValue(), ValueOrder.of().getValue())));
    }

    while (thatIterator.hasNext()) {
      TableIndexColumn thatNext = thatIterator.next();
      diffTableIndexColumnList.add(DiffTableIndexColumn.of(
              DiffValueName.of(ValueName.of().getValue(), thatNext.getName().getValue()),
              DiffValueOrder.of(ValueOrder.of().getValue(), thatNext.getOrder().getValue())));
    }

    return DiffTableIndexColumns.of(diffTableIndexColumnList);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TableIndexColumns that = (TableIndexColumns) o;
    return Objects.equals(getTableIndexColumnList(), that.getTableIndexColumnList());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTableIndexColumnList().toArray(new Object[0]));
  }

  @Override
  public Iterator<TableIndexColumn> iterator() {
    return tableIndexColumnList.iterator();
  }

  public static TableIndexColumns of() {
    return new TableIndexColumns();
  }

  public static TableIndexColumns of(TableIndexColumn... tableIndexColumns) {
    TableIndexColumns of = new TableIndexColumns();
    if (tableIndexColumns == null) {
      return of;
    }
    for (TableIndexColumn tableIndexColumn : tableIndexColumns) {
      of.addTableIndexColumn(tableIndexColumn);
    }
    return of;
  }

}
