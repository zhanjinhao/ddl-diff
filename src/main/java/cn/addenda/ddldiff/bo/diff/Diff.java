package cn.addenda.ddldiff.bo.diff;

import java.util.List;

public interface Diff {

  String EQUALS = "\"equals\"";

  String diff();

  class NullChecker {

    @Override
    public boolean equals(Object value) {
      if (value == null) {
        return true;
      }
      if (value instanceof DiffTable) {
        return DiffTable.ifNull((DiffTable) value);
      }
      if (value instanceof DiffTableColumn) {
        return DiffTableColumn.ifNull((DiffTableColumn) value);
      }
      if (value instanceof DiffTableColumns) {
        return DiffTableColumns.ifNull((DiffTableColumns) value);
      }
      if (value instanceof DiffTableIndex) {
        return DiffTableIndex.ifNull((DiffTableIndex) value);
      }
      if (value instanceof DiffTableIndexColumn) {
        return DiffTableIndexColumn.ifNull((DiffTableIndexColumn) value);
      }
      if (value instanceof DiffTableIndexColumns) {
        return DiffTableIndexColumns.ifNull((DiffTableIndexColumns) value);
      }
      if (value instanceof DiffTableIndexes) {
        return DiffTableIndexes.ifNull((DiffTableIndexes) value);
      }
      if (value instanceof DiffTableIndexType) {
        return DiffTableIndexType.ifNull((DiffTableIndexType) value);
      }
      if (value instanceof DiffValueBoolean) {
        return DiffValueBoolean.ifNull((DiffValueBoolean) value);
      }
      if (value instanceof DiffValueComment) {
        return DiffValueComment.ifNull((DiffValueComment) value);
      }
      if (value instanceof DiffValueName) {
        return DiffValueName.ifNull((DiffValueName) value);
      }
      if (value instanceof DiffValueOrder) {
        return DiffValueOrder.ifNull((DiffValueOrder) value);
      }
      if (value instanceof DiffValueString) {
        return DiffValueString.ifNull((DiffValueString) value);
      }
      if (value instanceof DiffValueType) {
        return DiffValueType.ifNull((DiffValueType) value);
      }
      if (value instanceof List<?>) {
        return ((List<?>) value).isEmpty();
      }
      return false;
    }
  }

}
