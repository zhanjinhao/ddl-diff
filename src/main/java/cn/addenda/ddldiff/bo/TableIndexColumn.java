package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumn;
import cn.addenda.ddldiff.jackson.deserializer.TableIndexColumnDeserializer;
import cn.addenda.ddldiff.jackson.serializer.TableIndexColumnSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.Objects;

@Getter
@JsonSerialize(using = TableIndexColumnSerializer.class)
@JsonDeserialize(using = TableIndexColumnDeserializer.class)
public class TableIndexColumn implements DiffAble<TableIndexColumn, DiffTableIndexColumn> {

  private ValueName name = ValueName.of();

  private ValueOrder order = ValueOrder.of();

  private TableIndexColumn() {
  }

  @Override
  public TableIndexColumn deepClone() {
    return TableIndexColumn.of(name.deepClone(), order.deepClone());
  }

  @Override
  public boolean absolutelyEquals(TableIndexColumn that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(TableIndexColumn o) {
    if (this == o) return true;
    if (o == null) {
      o = of();
    }
    if (getClass() != o.getClass()) return false;
    return getName().runtimeEquals(o.getName())
            && getOrder().runtimeEquals(o.getOrder());
  }

  @Override
  public DiffTableIndexColumn absolutelyDiff(TableIndexColumn that) {
    if (that == null) {
      that = of();
    }
    return DiffTableIndexColumn.of(
            this.getName().absolutelyDiff(that.getName()),
            this.getOrder().absolutelyDiff(that.getOrder()));
  }

  @Override
  public DiffTableIndexColumn runtimeDiff(TableIndexColumn that) {
    if (that == null) {
      that = of();
    }
    return DiffTableIndexColumn.of(
            this.getName().runtimeDiff(that.getName()),
            this.getOrder().runtimeDiff(that.getOrder()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TableIndexColumn that = (TableIndexColumn) o;
    return Objects.equals(getName(), that.getName())
            && Objects.equals(getOrder(), that.getOrder());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getOrder());
  }

  public static TableIndexColumn of(ValueName name, ValueOrder order) {
    TableIndexColumn tableColumn = new TableIndexColumn();
    if (name != null) {
      tableColumn.name = name;
    }
    if (order != null) {
      tableColumn.order = order;
    }
    return tableColumn;
  }

  public static TableIndexColumn of() {
    return new TableIndexColumn();
  }

  public static TableIndexColumnBuilder builder() {
    return new TableIndexColumnBuilder();
  }

  public static class TableIndexColumnBuilder {

    private ValueName name;

    private ValueOrder order;

    public TableIndexColumnBuilder name(ValueName name) {
      this.name = name;
      return this;
    }

    public TableIndexColumnBuilder order(ValueOrder order) {
      this.order = order;
      return this;
    }

    public TableIndexColumn build() {
      return TableIndexColumn.of(name, order);
    }

  }

}
