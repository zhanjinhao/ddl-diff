package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffTableColumn;
import cn.addenda.ddldiff.bo.diff.DiffTableColumns;
import cn.addenda.ddldiff.jackson.deserializer.TableColumnsDeserializer;
import cn.addenda.ddldiff.jackson.serializer.TableColumnsSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Getter
@JsonSerialize(using = TableColumnsSerializer.class)
@JsonDeserialize(using = TableColumnsDeserializer.class)
public class TableColumns implements DiffAble<TableColumns, DiffTableColumns>, Iterable<TableColumn> {

  private final List<TableColumn> tableColumnList = new ArrayList<>();

  private TableColumns() {
  }

  public void addTableColumn(TableColumn tableColumn) {
    if (tableColumn == null) {
      throw new UnsupportedOperationException("can not add null.");
    }
    this.tableColumnList.add(tableColumn);
  }

  public TableColumn removeTableColumnRuntime(ValueName columnName) {
    for (Iterator<TableColumn> iterator = tableColumnList.iterator(); iterator.hasNext(); ) {
      TableColumn tableColumn = iterator.next();
      if (tableColumn.getName().runtimeEquals(columnName)) {
        iterator.remove();
        return tableColumn;
      }
    }
    return null;
  }

  public TableColumn removeTableColumnAbsolutely(ValueName columnName) {
    for (Iterator<TableColumn> iterator = tableColumnList.iterator(); iterator.hasNext(); ) {
      TableColumn tableColumn = iterator.next();
      if (tableColumn.getName().absolutelyEquals(columnName)) {
        iterator.remove();
        return tableColumn;
      }
    }
    return null;
  }

  public int columnSize() {
    return tableColumnList.size();
  }

  @Override
  public TableColumns deepClone() {
    TableColumns tableColumns = new TableColumns();
    for (TableColumn tableColumn : tableColumnList) {
      tableColumns.addTableColumn(tableColumn.deepClone());
    }
    return tableColumns;
  }

  @Override
  public boolean absolutelyEquals(TableColumns that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(TableColumns that) {
    if (that == null) {
      that = TableColumns.of();
    }
    that = that.deepClone();

    for (TableColumn tableColumn : tableColumnList) {
      TableColumn thatTableColumn = that.removeTableColumnRuntime(tableColumn.getName());
      if (thatTableColumn == null) {
        return false;
      }
      if (!tableColumn.runtimeEquals(thatTableColumn)) {
        return false;
      }
    }

    return that.columnSize() == 0;
  }

  @Override
  public DiffTableColumns absolutelyDiff(TableColumns that) {
    if (that == null) {
      that = TableColumns.of();
    }
    List<DiffTableColumn> diffTableColumnList = new ArrayList<>();

    Iterator<TableColumn> iterator = this.iterator();
    Iterator<TableColumn> thatIterator = that.iterator();

    while (iterator.hasNext() && thatIterator.hasNext()) {
      TableColumn next = iterator.next();
      TableColumn thatNext = thatIterator.next();
      DiffTableColumn diffTableColumn = next.absolutelyDiff(thatNext);
      if (!DiffTableColumn.ifNull(diffTableColumn)) {
        diffTableColumnList.add(diffTableColumn);
      }
    }

    while (iterator.hasNext()) {
      TableColumn next = iterator.next();
      DiffTableColumn diffTableColumn = next.absolutelyDiff(TableColumn.of());
      if (!DiffTableColumn.ifNull(diffTableColumn)) {
        diffTableColumnList.add(diffTableColumn);
      }
    }

    while (thatIterator.hasNext()) {
      TableColumn thatNext = thatIterator.next();
      DiffTableColumn diffTableColumn = TableColumn.of().absolutelyDiff(thatNext);
      if (!DiffTableColumn.ifNull(diffTableColumn)) {
        diffTableColumnList.add(diffTableColumn);
      }
    }

    return DiffTableColumns.of(diffTableColumnList);
  }

  @Override
  public DiffTableColumns runtimeDiff(TableColumns that) {
    if (that == null) {
      that = TableColumns.of();
    }
    that = that.deepClone();

    List<DiffTableColumn> diffTableColumnList = new ArrayList<>();

    for (TableColumn tableColumn : tableColumnList) {
      TableColumn thatTableColumn = that.removeTableColumnRuntime(tableColumn.getName());
      if (thatTableColumn == null) {
        DiffTableColumn diffTableColumn = tableColumn.runtimeDiff(TableColumn.of());
        if (!DiffTableColumn.ifNull(diffTableColumn)) {
          diffTableColumnList.add(diffTableColumn);
        }
      } else {
        DiffTableColumn diffTableColumn = tableColumn.runtimeDiff(thatTableColumn);
        if (!DiffTableColumn.ifNull(diffTableColumn)) {
          diffTableColumnList.add(diffTableColumn);
        }
      }
    }

    for (TableColumn thatTableColumn : that) {
      DiffTableColumn diffTableColumn = TableColumn.of().runtimeDiff(thatTableColumn);
      if (!DiffTableColumn.ifNull(diffTableColumn)) {
        diffTableColumnList.add(diffTableColumn);
      }
    }

    return DiffTableColumns.of(diffTableColumnList);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TableColumns that = (TableColumns) o;
    return Objects.equals(getTableColumnList(), that.getTableColumnList());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTableColumnList().toArray(new Object[0]));
  }

  @Override
  public Iterator<TableColumn> iterator() {
    return tableColumnList.iterator();
  }

  public static TableColumns of() {
    return new TableColumns();
  }

  public static TableColumns of(TableColumn... tableColumns) {
    TableColumns of = TableColumns.of();
    if (tableColumns == null) {
      return of;
    }
    for (TableColumn tableColumn : tableColumns) {
      of.addTableColumn(tableColumn);
    }
    return of;
  }

}
