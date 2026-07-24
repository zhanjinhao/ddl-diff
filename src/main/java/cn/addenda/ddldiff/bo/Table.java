package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.ComparedKey;
import cn.addenda.ddldiff.bo.diff.DiffTable;
import cn.addenda.ddldiff.jackson.deserializer.TableDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import java.util.Objects;

@Getter
@JsonDeserialize(using = TableDeserializer.class)
public class Table implements DiffAble<Table, DiffTable> {

  private ValueName name = ValueName.of();

  private TableColumns tableColumns = TableColumns.of();

  private TableIndexes tableIndexes = TableIndexes.of();

  private ValueString engine = ValueString.of();

  private ValueString charset = ValueString.of();

  private ValueString collate = ValueString.of();

  private ValueComment comment = ValueComment.of();

  private Table() {
  }

  @Override
  public Table deepClone() {
    return Table.of(this.getName().deepClone(), this.getTableColumns().deepClone(),
            this.getTableIndexes().deepClone(), this.getEngine().deepClone(),
            this.getCharset().deepClone(), this.getCollate().deepClone(),
            this.getComment().deepClone());
  }

  @Override
  public boolean absolutelyEquals(Table that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(Table that) {
    if (this == that) return true;
    if (that == null) {
      that = of();
    }
    if (getClass() != that.getClass()) return false;
    return getName().runtimeEquals(that.getName())
            && getTableColumns().runtimeEquals(that.getTableColumns())
            && getTableIndexes().runtimeEquals(that.getTableIndexes())
            && Objects.equals(getEngine(), that.getEngine())
            && Objects.equals(getCharset(), that.getCharset())
            && Objects.equals(getCollate(), that.getCollate())
            && Objects.equals(getComment(), that.getComment());
  }

  @Override
  public DiffTable absolutelyDiff(Table that) {
    if (that == null) {
      that = of();
    }
    return DiffTable.of(
            ComparedKey.of(getName(), that.getName()),
            getName().absolutelyDiff(that.getName()),
            getTableColumns().absolutelyDiff(that.getTableColumns()),
            getTableIndexes().absolutelyDiff(that.getTableIndexes()),
            getEngine().absolutelyDiff(that.getEngine()),
            getCharset().absolutelyDiff(that.getCharset()),
            getCollate().absolutelyDiff(that.getCollate()),
            getComment().absolutelyDiff(that.getComment()));
  }

  @Override
  public DiffTable runtimeDiff(Table that) {
    if (that == null) {
      that = of();
    }
    return DiffTable.of(
            ComparedKey.of(getName(), that.getName()),
            getName().runtimeDiff(that.getName()),
            getTableColumns().runtimeDiff(that.getTableColumns()),
            getTableIndexes().runtimeDiff(that.getTableIndexes()),
            getEngine().runtimeDiff(that.getEngine()),
            getCharset().runtimeDiff(that.getCharset()),
            getCollate().runtimeDiff(that.getCollate()),
            getComment().runtimeDiff(that.getComment()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Table table = (Table) o;
    return Objects.equals(getName(), table.getName())
            && Objects.equals(getTableColumns(), table.getTableColumns())
            && Objects.equals(getTableIndexes(), table.getTableIndexes())
            && Objects.equals(getEngine(), table.getEngine())
            && Objects.equals(getCharset(), table.getCharset())
            && Objects.equals(getCollate(), table.getCollate())
            && Objects.equals(getComment(), table.getComment());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getTableColumns(),
            getTableIndexes(), getEngine(),
            getCharset(), getCollate(), getComment());
  }

  public static Table of(ValueName name, TableColumns tableColumns,
                         TableIndexes tableIndexes, ValueString engine,
                         ValueString charset, ValueString collate, ValueComment comment) {
    Table table = new Table();
    if (name != null) {
      table.name = name;
    }
    if (tableColumns != null) {
      table.tableColumns = tableColumns;
    }
    if (tableIndexes != null) {
      table.tableIndexes = tableIndexes;
    }
    if (engine != null) {
      table.engine = engine;
    }
    if (charset != null) {
      table.charset = charset;
    }
    if (collate != null) {
      table.collate = collate;
    }
    if (comment != null) {
      table.comment = comment;
    }
    return table;
  }

  private static Table of() {
    return new Table();
  }

  public static TableBuilder builder() {
    return new TableBuilder();
  }

  public static class TableBuilder {

    private ValueName name;

    private TableColumns tableColumns;

    private TableIndexes tableIndexes;

    private ValueString engine;

    private ValueString charset;

    private ValueString collate;

    private ValueComment comment;

    public TableBuilder name(ValueName name) {
      this.name = name;
      return this;
    }

    public TableBuilder tableColumns(TableColumns tableColumns) {
      this.tableColumns = tableColumns;
      return this;
    }

    public TableBuilder tableIndexes(TableIndexes tableIndexes) {
      this.tableIndexes = tableIndexes;
      return this;
    }

    public TableBuilder engine(ValueString engine) {
      this.engine = engine;
      return this;
    }

    public TableBuilder charset(ValueString charset) {
      this.charset = charset;
      return this;
    }

    public TableBuilder collate(ValueString collate) {
      this.collate = collate;
      return this;
    }

    public TableBuilder comment(ValueComment comment) {
      this.comment = comment;
      return this;
    }

    public Table build() {
      return Table.of(name, tableColumns, tableIndexes, engine, charset, collate, comment);
    }

  }

}
