package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.ComparedKey;
import cn.addenda.ddldiff.bo.diff.DiffTableIndex;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexType;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TableIndex implements DiffAble<TableIndex, DiffTableIndex> {

  public static final String PRIMARY_KEY_NAME = "_PRIMARY_KEY_";

  private TableIndexColumns tableIndexColumns = TableIndexColumns.of();

  private ValueName name = ValueName.of();

  private IndexType indexType;

  private ValueComment comment = ValueComment.of();

  private TableIndex() {
  }

  @Override
  public TableIndex deepClone() {
    return TableIndex.of(tableIndexColumns.deepClone(), name.deepClone(), indexType, comment.deepClone());
  }

  @Override
  public boolean absolutelyEquals(TableIndex that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(TableIndex o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TableIndex that = o;
    return getTableIndexColumns().runtimeEquals(that.getTableIndexColumns())
            && getName().runtimeEquals(that.getName())
            && getIndexType() == that.getIndexType()
            && getComment().runtimeEquals(that.getComment());
  }

  @Override
  public DiffTableIndex absolutelyDiff(TableIndex that) {
    return DiffTableIndex.of(
            ComparedKey.of(getName(), that.getName()),
            getTableIndexColumns().absolutelyDiff(that.getTableIndexColumns()),
            getName().absolutelyDiff(that.getName()),
            DiffTableIndexType.of(getIndexType(), that.getIndexType()),
            getComment().absolutelyDiff(that.getComment()));
  }

  @Override
  public DiffTableIndex runtimeDiff(TableIndex that) {
    return DiffTableIndex.of(
            ComparedKey.of(getName(), that.getName()),
            getTableIndexColumns().runtimeDiff(that.getTableIndexColumns()),
            getName().runtimeDiff(that.getName()),
            DiffTableIndexType.of(getIndexType(), that.getIndexType()),
            getComment().runtimeDiff(that.getComment()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TableIndex that = (TableIndex) o;
    return Objects.equals(getTableIndexColumns(), that.getTableIndexColumns())
            && Objects.equals(getName(), that.getName())
            && getIndexType() == that.getIndexType()
            && Objects.equals(getComment(), that.getComment());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
            getTableIndexColumns(), getName(),
            getIndexType(), getComment());
  }

  public static TableIndex of(TableIndexColumns tableIndexColumns, ValueName name,
                              IndexType indexType, ValueComment comment) {
    TableIndex tableIndex = new TableIndex();
    if (tableIndexColumns != null) {
      tableIndex.tableIndexColumns = tableIndexColumns;
    }
    if (name != null) {
      tableIndex.name = name;
    }
    if (indexType != null) {
      tableIndex.indexType = indexType;
    }
    if (comment != null) {
      tableIndex.comment = comment;
    }
    return tableIndex;
  }

  public static TableIndex of() {
    return new TableIndex();
  }

  public static TableIndexBuilder builder() {
    return new TableIndexBuilder();
  }

  public static class TableIndexBuilder {

    private TableIndexColumns tableIndexColumns;

    private ValueName name;

    private IndexType indexType;

    private ValueComment comment;

    public TableIndexBuilder tableIndexColumns(TableIndexColumns tableIndexColumns) {
      this.tableIndexColumns = tableIndexColumns;
      return this;
    }

    public TableIndexBuilder name(ValueName name) {
      this.name = name;
      return this;
    }

    public TableIndexBuilder indexType(IndexType indexType) {
      this.indexType = indexType;
      return this;
    }

    public TableIndexBuilder comment(ValueComment comment) {
      this.comment = comment;
      return this;
    }

    public TableIndex build() {
      if (indexType == null) {
        throw new IllegalArgumentException("indexType of TableIndex can not be null.");
      }
      return TableIndex.of(tableIndexColumns, name, indexType, comment);
    }

  }

}
