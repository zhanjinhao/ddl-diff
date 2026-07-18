package cn.addenda.ddldiff.parse;

import cn.addenda.ddldiff.bo.*;
import lombok.SneakyThrows;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableParser {

  private static final String EMPTY = " ";
  private static final String PRIMARY_KEY = "PRIMARY KEY";
  private static final String UNIQUE_KEY = "UNIQUE KEY";
  private static final String KEY = "KEY";
  private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
  private static final String ENGINE = "ENGINE";
  private static final String EQUAL = "=";
  private static final String CHARSET = "CHARSET";
  private static final String CHARACTER = "CHARACTER";
  private static final String SET = "SET";
  private static final String COLLATE = "COLLATE";
  private static final String NOT = "NOT";
  private static final String NULL = "NULL";
  private static final String COMMENT = "COMMENT";
  private static final String DEFAULT = "DEFAULT";

  @SneakyThrows
  public Table parse(String ddl) {
    CreateTable createTableStatement = (CreateTable) CCJSqlParserUtil.parse(ddl);

    Table.TableBuilder tableBuilder = Table.builder();

    tableBuilder.name(ValueName.of(parseName(createTableStatement)));
    tableBuilder.tableColumns(parseTableColumns(createTableStatement));
    tableBuilder.tableIndexes(parseTableIndexes(createTableStatement));

    List<String> tableOptionList = createTableStatement.getTableOptionsStrings();
    if (tableOptionList != null) {
      for (int i = 0; i < tableOptionList.size(); i++) {
        String tableOption = tableOptionList.get(i);
        if (ENGINE.equalsIgnoreCase(tableOption) && i + 2 < tableOptionList.size() && EQUAL.equalsIgnoreCase(tableOptionList.get(i + 1))) {
          tableBuilder.engine(ValueString.of(tableOptionList.get(i + 2)));
        }
        if (CHARSET.equalsIgnoreCase(tableOption) && i + 2 < tableOptionList.size() && EQUAL.equalsIgnoreCase(tableOptionList.get(i + 1))) {
          tableBuilder.charset(ValueString.of(tableOptionList.get(i + 2)));
        }
        if (COLLATE.equalsIgnoreCase(tableOption) && i + 2 < tableOptionList.size() && EQUAL.equalsIgnoreCase(tableOptionList.get(i + 1))) {
          tableBuilder.collate(ValueString.of(tableOptionList.get(i + 2)));
        }
        if (COMMENT.equalsIgnoreCase(tableOption) && i + 2 < tableOptionList.size() && EQUAL.equalsIgnoreCase(tableOptionList.get(i + 1))) {
          tableBuilder.comment(ValueComment.of(tableOptionList.get(i + 2)));
        }
      }
    }

    return tableBuilder.build();
  }

  private String parseName(CreateTable createTableStatement) {
    net.sf.jsqlparser.schema.Table tableStatement = createTableStatement.getTable();
    return tableStatement.getFullyQualifiedName();
  }

  private TableColumns parseTableColumns(CreateTable createTableStatement) {
    List<ColumnDefinition> columnDefinitionList = createTableStatement.getColumnDefinitions();
    if (columnDefinitionList == null || columnDefinitionList.isEmpty()) {
      throw new IllegalArgumentException("table has at least 1 column.");
    }
    TableColumns tableColumns = TableColumns.of();
    for (ColumnDefinition columnDefinition : columnDefinitionList) {

      TableColumn.TableColumnBuilder tableColumnBuilder = TableColumn.builder();

      String columnName = columnDefinition.getColumnName();
      tableColumnBuilder.name(ValueName.of(columnName));

      ColDataType colDataType = columnDefinition.getColDataType();
      tableColumnBuilder.type(ValueType.of(assembleType(colDataType)));
      tableColumnBuilder.charset(ValueString.of(colDataType.getCharacterSet()));

      List<String> columnSpecList = columnDefinition.getColumnSpecs();
      if (columnSpecList == null) {
        columnSpecList = new ArrayList<>();
      }
      for (int i = 0; i < columnSpecList.size(); i++) {
        String columnSpec = columnSpecList.get(i);

        if (COLLATE.equals(columnSpec) && i + 1 < columnSpecList.size()) {
          tableColumnBuilder.collate(ValueString.of(columnSpecList.get(i + 1)));
        }
        if (AUTO_INCREMENT.equals(columnSpec)) {
          tableColumnBuilder.ifAutoIncrement(ValueBoolean.TRUE);
        }
        if (COMMENT.equals(columnSpec) && i + 1 < columnSpecList.size()) {
          tableColumnBuilder.comment(ValueComment.of(columnSpecList.get(i + 1)));
        }
        if (NOT.equals(columnSpec) && i + 1 < columnSpecList.size() && NULL.equals(columnSpecList.get(i + 1))) {
          tableColumnBuilder.ifNullable(ValueBoolean.FALSE);
        }
        if (DEFAULT.equals(columnSpec) && i + 1 < columnSpecList.size()) {
          if (NULL.equals(columnSpecList.get(i + 1))) {
//            tableColumnBuilder.defaultValue(ValueString.of());
            tableColumnBuilder.ifNullable(ValueBoolean.TRUE);
          } else {
            StringBuilder sb = new StringBuilder();
            for (int j = i + 1; j < columnSpecList.size(); j++) {
              String dv = columnSpecList.get(j);
              if (COMMENT.equals(dv)) {
                break;
              }
              sb.append(EMPTY).append(dv);
            }
            tableColumnBuilder.defaultValue(ValueString.of(sb.substring(1)));
          }
        }
      }

      tableColumns.addTableColumn(tableColumnBuilder.build());
    }

    return tableColumns;
  }

  private TableIndexes parseTableIndexes(CreateTable createTableStatement) {
    List<Index> indexList = createTableStatement.getIndexes();
    if (indexList == null) {
      return TableIndexes.of();
    }
    TableIndexes tableIndexes = TableIndexes.of();

    for (Index index : indexList) {
      TableIndex.TableIndexBuilder tableIndexBuilder = TableIndex.builder();

      String type = index.getType();
      switch (type) {
        case PRIMARY_KEY:
          tableIndexBuilder.name(ValueName.of(TableIndex.PRIMARY_KEY_NAME));
          tableIndexBuilder.indexType(IndexType.PRIMARY);
          break;
        case UNIQUE_KEY:
          tableIndexBuilder.name(ValueName.of(index.getName()));
          tableIndexBuilder.indexType(IndexType.UNIQUE);
          break;
        case KEY:
          tableIndexBuilder.name(ValueName.of(index.getName()));
          tableIndexBuilder.indexType(IndexType.SIMPLE);
          break;
        default:
          throw new UnsupportedOperationException(type);
      }

      List<String> indexSpecList = index.getIndexSpec();
      if (indexSpecList == null) {
//        tableIndexBuilder.comment(ValueString.of());
      } else {
        StringBuilder comment = new StringBuilder();
        for (String indexSpec : indexSpecList) {
          if (!COMMENT.equalsIgnoreCase(indexSpec)) {
            comment.append(indexSpec).append(EMPTY);
          }
        }
        tableIndexBuilder.comment(ValueComment.of(comment.length() == 0 ? null : comment.substring(0, comment.length() - 1)));
      }

      TableIndexColumns tableIndexColumns = TableIndexColumns.of();
      tableIndexBuilder.tableIndexColumns(tableIndexColumns);
      List<Index.ColumnParams> columnParamsList = index.getColumns();
      for (Index.ColumnParams columnParams : columnParamsList) {

        TableIndexColumn.TableIndexColumnBuilder tableIndexColumnBuilder = TableIndexColumn.builder();

        String columnName = columnParams.getColumnName();
        tableIndexColumnBuilder.name(ValueName.of(columnName));
        List<String> params = columnParams.getParams();
        if (params != null) {
          tableIndexColumnBuilder.order(ValueOrder.of(String.join(EMPTY, params)));
        }

        tableIndexColumns.addTableIndexColumn(tableIndexColumnBuilder.build());
      }

      tableIndexes.addTableIndex(tableIndexBuilder.build());
    }

    return tableIndexes;
  }

  private String assembleType(ColDataType colDataType) {
    String dataType = colDataType.getDataType();
    List<String> argumentsStringList = colDataType.getArgumentsStringList();
    if (argumentsStringList == null) {
      return dataType;
    }

    return dataType + argumentsStringList.stream().collect(Collectors.joining(",", "(", ")"));
  }

}
