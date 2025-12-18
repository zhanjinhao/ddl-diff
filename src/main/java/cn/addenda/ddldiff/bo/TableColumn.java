package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.ComparedKey;
import cn.addenda.ddldiff.bo.diff.DiffTableColumn;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TableColumn implements DiffAble<TableColumn, DiffTableColumn> {

  private ValueName name = ValueName.of();

  private ValueType type = ValueType.of();

  private ValueString charset = ValueString.of();

  private ValueString collate = ValueString.of();

  private ValueString defaultValue = ValueString.of();

  private ValueBoolean ifNullable = ValueBoolean.TRUE;

  private ValueBoolean ifAutoIncrement = ValueBoolean.FALSE;

  private ValueComment comment = ValueComment.of();

  private TableColumn() {
  }

  @Override
  public TableColumn deepClone() {
    return TableColumn.of(
            this.getName().deepClone(), this.getType().deepClone(),
            this.getCharset().deepClone(), this.getCollate().deepClone(),
            this.getDefaultValue().deepClone(), this.getIfNullable().deepClone(),
            this.getIfAutoIncrement().deepClone(), this.getComment().deepClone());
  }

  @Override
  public boolean absolutelyEquals(TableColumn that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(TableColumn o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return getName().runtimeEquals(o.getName())
            && getType().runtimeEquals(o.getType())
            && getCharset().runtimeEquals(o.getCharset())
            && getCollate().runtimeEquals(o.getCollate())
            && getDefaultValue().runtimeEquals(o.getDefaultValue())
            && getIfNullable().runtimeEquals(o.getIfNullable())
            && getIfAutoIncrement().runtimeEquals(o.getIfAutoIncrement())
            && getComment().runtimeEquals(o.getComment());
  }

  @Override
  public DiffTableColumn absolutelyDiff(TableColumn that) {
    if (that == null) {
      that = TableColumn.of();
    }
    return DiffTableColumn.of(
            ComparedKey.of(getName(), that.getName()),
            this.getName().absolutelyDiff(that.getName()),
            this.getType().absolutelyDiff(that.getType()),
            this.getCharset().absolutelyDiff(that.getCharset()),
            this.getCollate().absolutelyDiff(that.getCollate()),
            this.getDefaultValue().absolutelyDiff(that.getDefaultValue()),
            this.getIfNullable().absolutelyDiff(that.getIfNullable()),
            this.getIfAutoIncrement().absolutelyDiff(that.getIfAutoIncrement()),
            this.getComment().absolutelyDiff(that.getComment()));
  }

  @Override
  public DiffTableColumn runtimeDiff(TableColumn that) {
    if (that == null) {
      that = TableColumn.of();
    }
    return DiffTableColumn.of(
            ComparedKey.of(getName(), that.getName()),
            this.getName().runtimeDiff(that.getName()),
            this.getType().runtimeDiff(that.getType()),
            this.getCharset().runtimeDiff(that.getCharset()),
            this.getCollate().runtimeDiff(that.getCollate()),
            this.getDefaultValue().runtimeDiff(that.getDefaultValue()),
            this.getIfNullable().runtimeDiff(that.getIfNullable()),
            this.getIfAutoIncrement().runtimeDiff(that.getIfAutoIncrement()),
            this.getComment().runtimeDiff(that.getComment()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TableColumn that = (TableColumn) o;
    return Objects.equals(getName(), that.getName())
            && Objects.equals(getType(), that.getType())
            && Objects.equals(getCharset(), that.getCharset())
            && Objects.equals(getCollate(), that.getCollate())
            && Objects.equals(getDefaultValue(), that.getDefaultValue())
            && Objects.equals(getIfNullable(), that.getIfNullable())
            && Objects.equals(getIfAutoIncrement(), that.getIfAutoIncrement())
            && Objects.equals(getComment(), that.getComment());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getType(),
            getCharset(), getCollate(),
            getDefaultValue(), getIfNullable(),
            getIfAutoIncrement(), getComment());
  }

  public static TableColumn of(ValueName name, ValueType type,
                               ValueString charset, ValueString collate,
                               ValueString defaultValue, ValueBoolean ifNullable,
                               ValueBoolean ifAutoIncrement, ValueComment comment) {
    TableColumn tableColumn = new TableColumn();
    if (name != null) {
      tableColumn.name = name;
    }
    if (type != null) {
      tableColumn.type = type;
    }
    if (charset != null) {
      tableColumn.charset = charset;
    }
    if (collate != null) {
      tableColumn.collate = collate;
    }
    if (defaultValue != null) {
      tableColumn.defaultValue = defaultValue;
    }
    if (ifNullable != null) {
      tableColumn.ifNullable = ifNullable;
    }
    if (ifAutoIncrement != null) {
      tableColumn.ifAutoIncrement = ifAutoIncrement;
    }
    if (comment != null) {
      tableColumn.comment = comment;
    }
    return tableColumn;
  }

  public static TableColumn of() {
    return new TableColumn();
  }

  public static TableColumnBuilder builder() {
    return new TableColumnBuilder();
  }

  public static class TableColumnBuilder {

    private ValueName name;

    private ValueType type;

    private ValueString charset;

    private ValueString collate;

    private ValueString defaultValue;

    private ValueBoolean ifNullable;

    private ValueBoolean ifAutoIncrement;

    private ValueComment comment;

    public TableColumnBuilder name(ValueName name) {
      this.name = name;
      return this;
    }

    public TableColumnBuilder type(ValueType type) {
      this.type = type;
      return this;
    }

    public TableColumnBuilder charset(ValueString charset) {
      this.charset = charset;
      return this;
    }

    public TableColumnBuilder collate(ValueString collate) {
      this.collate = collate;
      return this;
    }

    public TableColumnBuilder defaultValue(ValueString defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    public TableColumnBuilder ifNullable(ValueBoolean ifNullable) {
      this.ifNullable = ifNullable;
      return this;
    }

    public TableColumnBuilder ifAutoIncrement(ValueBoolean ifAutoIncrement) {
      this.ifAutoIncrement = ifAutoIncrement;
      return this;
    }

    public TableColumnBuilder comment(ValueComment comment) {
      this.comment = comment;
      return this;
    }

    public TableColumn build() {
      return TableColumn.of(name, type, charset, collate, defaultValue, ifNullable, ifAutoIncrement, comment);
    }

  }


}
