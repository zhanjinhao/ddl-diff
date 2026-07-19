package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.DiffTableIndex;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexes;
import cn.addenda.ddldiff.jackson.deserializer.TableIndexesDeserializer;
import cn.addenda.ddldiff.jackson.serializer.TableIndexesSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Getter
@JsonSerialize(using = TableIndexesSerializer.class)
@JsonDeserialize(using = TableIndexesDeserializer.class)
public class TableIndexes implements DiffAble<TableIndexes, DiffTableIndexes>, Iterable<TableIndex> {

  private final List<TableIndex> tableIndexList = new ArrayList<>();

  private TableIndexes() {
  }

  public void addTableIndex(TableIndex tableIndex) {
    if (tableIndex == null) {
      throw new UnsupportedOperationException("can not add null.");
    }
    tableIndexList.add(tableIndex);
  }

  public TableIndex removeTableIndexRuntime(ValueName indexName) {
    for (Iterator<TableIndex> iterator = tableIndexList.iterator(); iterator.hasNext(); ) {
      TableIndex tableIndex = iterator.next();
      if (tableIndex.getName().runtimeEquals(indexName)) {
        iterator.remove();
        return tableIndex;
      }
    }
    return null;
  }

  public TableIndex removeTableIndexAbsolutely(ValueName indexName) {
    for (Iterator<TableIndex> iterator = tableIndexList.iterator(); iterator.hasNext(); ) {
      TableIndex tableIndex = iterator.next();
      if (tableIndex.getName().absolutelyEquals(indexName)) {
        iterator.remove();
        return tableIndex;
      }
    }
    return null;
  }

  public int indexSize() {
    return tableIndexList.size();
  }

  @Override
  public TableIndexes deepClone() {
    TableIndexes tableIndexes = TableIndexes.of();
    for (TableIndex tableIndex : tableIndexList) {
      tableIndexes.addTableIndex(tableIndex.deepClone());
    }
    return tableIndexes;
  }

  @Override
  public boolean absolutelyEquals(TableIndexes that) {
    return equals(that);
  }

  @Override
  public boolean runtimeEquals(TableIndexes that) {
    that = that.deepClone();

    for (TableIndex tableIndex : tableIndexList) {
      TableIndex thatTableIndex = that.removeTableIndexRuntime(tableIndex.getName());
      if (thatTableIndex == null) {
        return false;
      }
      if (!tableIndex.runtimeEquals(thatTableIndex)) {
        return false;
      }
    }

    return that.indexSize() == 0;
  }

  @Override
  public DiffTableIndexes absolutelyDiff(TableIndexes that) {
    List<DiffTableIndex> diffTableIndexList = new ArrayList<>();

    Iterator<TableIndex> iterator = this.iterator();
    Iterator<TableIndex> thatIterator = that.iterator();

    while (iterator.hasNext() && thatIterator.hasNext()) {
      TableIndex next = iterator.next();
      TableIndex thatNext = thatIterator.next();
      DiffTableIndex diffTableIndex = next.absolutelyDiff(thatNext);
      if (!DiffTableIndex.ifNull(diffTableIndex)) {
        diffTableIndexList.add(diffTableIndex);
      }
    }

    while (iterator.hasNext()) {
      TableIndex next = iterator.next();
      DiffTableIndex diffTableIndex = next.absolutelyDiff(TableIndex.of());
      if (!DiffTableIndex.ifNull(diffTableIndex)) {
        diffTableIndexList.add(diffTableIndex);
      }
    }

    while (thatIterator.hasNext()) {
      TableIndex thatNext = thatIterator.next();
      DiffTableIndex diffTableIndex = TableIndex.of().absolutelyDiff(thatNext);
      if (!DiffTableIndex.ifNull(diffTableIndex)) {
        diffTableIndexList.add(diffTableIndex);
      }
    }

    return DiffTableIndexes.of(diffTableIndexList);
  }

  @Override
  public DiffTableIndexes runtimeDiff(TableIndexes that) {
    that = that.deepClone();

    List<DiffTableIndex> diffTableIndexList = new ArrayList<>();

    for (TableIndex tableIndex : tableIndexList) {
      TableIndex thatTableIndex = that.removeTableIndexRuntime(tableIndex.getName());
      if (thatTableIndex == null) {
        DiffTableIndex diffTableIndex = tableIndex.runtimeDiff(TableIndex.of());
        if (!DiffTableIndex.ifNull(diffTableIndex)) {
          diffTableIndexList.add(diffTableIndex);
        }
      } else {
        DiffTableIndex diffTableIndex = tableIndex.runtimeDiff(thatTableIndex);
        if (!DiffTableIndex.ifNull(diffTableIndex)) {
          diffTableIndexList.add(diffTableIndex);
        }
      }
    }

    for (TableIndex thatTableIndex : that) {
      DiffTableIndex diffTableIndex = TableIndex.of().runtimeDiff(thatTableIndex);
      if (!DiffTableIndex.ifNull(diffTableIndex)) {
        diffTableIndexList.add(diffTableIndex);
      }
    }

    return DiffTableIndexes.of(diffTableIndexList);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TableIndexes that = (TableIndexes) o;
    return Objects.equals(getTableIndexList(), that.getTableIndexList());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTableIndexList().toArray(new Object[0]));
  }

  @Override
  public Iterator<TableIndex> iterator() {
    return tableIndexList.iterator();
  }

  public static TableIndexes of() {
    return new TableIndexes();
  }

  public static TableIndexes of(TableIndex... tableIndexes) {
    TableIndexes of = new TableIndexes();
    if (tableIndexes == null) {
      return of;
    }
    for (TableIndex tableIndex : tableIndexes) {
      of.addTableIndex(tableIndex);
    }
    return of;
  }

}
