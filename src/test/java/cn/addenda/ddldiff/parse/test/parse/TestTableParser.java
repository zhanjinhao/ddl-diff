package cn.addenda.ddldiff.parse.test.parse;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.*;
import cn.addenda.ddldiff.parse.TableParser;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class TestTableParser {

  String ddl = "CREATE TABLE `t_flight_pln_detail1` (\n" +
          "  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n" +
          "  `FLIGHT_ID` bigint DEFAULT NULL COMMENT '航班ID',\n" +
          "  `FLIGHT_DATE` datetime NOT NULL COMMENT '航班日期',\n" +
          "  `FLIGHT_NO` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '航班号',\n" +
          "  `DEPARTURE_AIRPORT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '起飞机场',\n" +
          "  `ARRIVAL_AIRPORT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '着陆机场',\n" +
          "  `PTD` datetime DEFAULT NULL COMMENT '局批时间',\n" +
          "  `STD` datetime DEFAULT NULL COMMENT '计飞时间',\n" +
          "  `DEP_SEND_FLAG` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '起飞机 场是否已发',\n" +
          "  `ARR_SEND_FLAG` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '着陆机场是否已发',\n" +
          "  `DEP_SEND_TIME` datetime DEFAULT NULL COMMENT '起飞机场发送时间',\n" +
          "  `ARR_SEND_TIME` datetime DEFAULT NULL COMMENT '着陆机场发送时间',\n" +
          "  `ARR_SEND_TIME2` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '着陆机场发送时间2',\n" +
          "  `ARR_SEND_TIME3` datetime NOT NULL COMMENT '着陆机场发送时间3',\n" +
          "  `ARR_SEND_TIME4` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '着陆机场发送时间4',\n" +
          "  `cost` decimal(4,2) DEFAULT NULL COMMENT '耗时',\n" +
          "  `CREATOR` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人的用户ID',\n" +
          "  `CREATE_TM` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
          "  `MODIFIER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后修改人的用户ID',\n" +
          "  `MODIFY_TM` datetime DEFAULT NULL COMMENT '最后修改时间',\n" +
          "  `DELETE_FLAG` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'N' COMMENT '是否删除',\n" +
          "  PRIMARY KEY (`ID`),\n" +
          "  UNIQUE KEY `idx` (`FLIGHT_ID`,`FLIGHT_NO` DESC),\n" +
          "  KEY `IDX_FLIGHT_PLN_DETAIL` (`FLIGHT_ID` DESC) COMMENT 'aa'\n" +
          ") ENGINE=InnoDB AUTO_INCREMENT=377979 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='航班PLN发送明细'";

  String json = "{\n" +
          "  \"name\": \"`t_flight_pln_detail1`\",\n" +
          "  \"tableColumns\": [\n" +
          "    {\n" +
          "      \"name\": \"`ID`\",\n" +
          "      \"type\": \"bigint\",\n" +
          "      \"charset\": null,\n" +
          "      \"collate\": null,\n" +
          "      \"defaultValue\": null,\n" +
          "      \"ifNullable\": false,\n" +
          "      \"ifAutoIncrement\": true,\n" +
          "      \"comment\": \"'主键ID'\"\n" +
          "    }\n" +
          "  ],\n" +
          "  \"tableIndexes\": [\n" +
          "    {\n" +
          "      \"tableIndexColumns\": [\n" +
          "        \"`ID`\"\n" +
          "      ],\n" +
          "      \"name\": \"_PRIMARY_KEY_\",\n" +
          "      \"indexType\": \"PRIMARY\",\n" +
          "      \"comment\": null\n" +
          "    },\n" +
          "    {\n" +
          "      \"tableIndexColumns\": [\n" +
          "        \"`FLIGHT_ID`\",\n" +
          "        \"`FLIGHT_NO` DESC\"\n" +
          "      ],\n" +
          "      \"name\": \"`idx`\",\n" +
          "      \"indexType\": \"UNIQUE\",\n" +
          "      \"comment\": null\n" +
          "    },\n" +
          "    {\n" +
          "      \"tableIndexColumns\": [\n" +
          "        \"`FLIGHT_ID` DESC\"\n" +
          "      ],\n" +
          "      \"name\": \"`IDX_FLIGHT_PLN_DETAIL`\",\n" +
          "      \"indexType\": \"SIMPLE\",\n" +
          "      \"comment\": \"'aa'\"\n" +
          "    }\n" +
          "  ],\n" +
          "  \"engine\": \"InnoDB\",\n" +
          "  \"charset\": \"utf8mb4\",\n" +
          "  \"collate\": \"utf8mb4_general_ci\",\n" +
          "  \"comment\": \"'航班PLN发送明细'\"\n" +
          "}";

  @Test
  void test1() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl);

    String json = JacksonUtils.toStr(table1);
    System.out.println(json);

    Table table2 = JacksonUtils.toObj(json, new TypeReference<Table>() {
    });

    Assertions.assertEquals(true, table1.equals(table2));
  }

  @Test
  void test2() {

    Table table = JacksonUtils.toObj(json, new TypeReference<Table>() {
    });

    String json2 = JacksonUtils.toStr(table);

    System.out.println(json);
    System.out.println(json2);

    Assertions.assertEquals(true, JacksonUtils.formatJson(json).equals(JacksonUtils.formatJson(json2)));
  }

  String ddl2 = "CREATE TABLE `t_closure_table` (\n" +
          "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
          "  `ancestor` bigint NOT NULL,\n" +
          "  `descendant` bigint NOT NULL,\n" +
          "  `distance` int NOT NULL,\n" +
          "  `creator` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `creator_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `create_time` datetime(3) NOT NULL,\n" +
          "  `modifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `modifier_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `modify_time` datetime(3) NOT NULL,\n" +
          "  PRIMARY KEY (`id`),\n" +
          "  KEY `t_closure_table_down` (`ancestor`,`descendant`,`distance`),\n" +
          "  KEY `t_closure_table_up` (`descendant`,`ancestor`,`distance`)\n" +
          ") ENGINE=InnoDB AUTO_INCREMENT=867 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

  @Test
  void test3() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl);
    Table table2 = tableParser.parse(ddl2);

    System.out.println(table1.runtimeDiff(table2).diff());

  }

  // ================================================================
  //              默认值测试 — 每个字段的默认值
  // ================================================================

  @Test
  void testParseDefaultValues() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int)");

    // Table 级别字段及其默认值
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of(), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of(), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());

    // TableColumn 级别字段及其默认值
    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), col.getName());
    Assertions.assertEquals(ValueType.of("int"), col.getType());
    Assertions.assertEquals(ValueString.of(), col.getCharset());
    Assertions.assertEquals(ValueString.of(), col.getCollate());
    Assertions.assertEquals(ValueString.of(), col.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, col.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, col.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), col.getComment());
  }

  // ================================================================
  //              指定值测试 — 每个字段的“指定值”
  // ================================================================

  String specifiedDdl = "CREATE TABLE `my_t` (\n" +
          "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'pk',\n" +
          "  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'N' COMMENT 'name',\n" +
          "  PRIMARY KEY (`id`),\n" +
          "  UNIQUE KEY `idx_name` (`name`),\n" +
          "  KEY `idx_id_name` (`id`, `name` desc) COMMENT 'index comment'\n" +
          ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='table comment'";

  @Test
  void testParseSpecifiedValues() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse(specifiedDdl);

    // ====== Table 级别指定值 ======
    Assertions.assertEquals(ValueName.of("`my_t`"), table.getName());
    Assertions.assertEquals(ValueString.of("InnoDB"), table.getEngine());
    Assertions.assertEquals(ValueString.of("utf8mb4"), table.getCharset());
    Assertions.assertEquals(ValueString.of("utf8mb4_unicode_ci"), table.getCollate());
    Assertions.assertEquals(ValueComment.of("'table comment'"), table.getComment());
    Assertions.assertEquals(2, table.getTableColumns().columnSize());
    Assertions.assertEquals(3, table.getTableIndexes().indexSize());

    // ====== TableColumn: `id` ======
    Iterator<TableColumn> colIt = table.getTableColumns().iterator();
    TableColumn idCol = colIt.next();
    Assertions.assertEquals(ValueName.of("`id`"), idCol.getName());
    Assertions.assertEquals(ValueType.of("bigint"), idCol.getType());
    Assertions.assertEquals(ValueString.of(), idCol.getCharset());
    Assertions.assertEquals(ValueString.of(), idCol.getCollate());
    Assertions.assertEquals(ValueString.of(), idCol.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.FALSE, idCol.getIfNullable());
    Assertions.assertEquals(ValueBoolean.TRUE, idCol.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of("'pk'"), idCol.getComment());

    // ====== TableColumn: `name` ======
    TableColumn nameCol = colIt.next();
    Assertions.assertEquals(ValueName.of("`name`"), nameCol.getName());
    Assertions.assertEquals(ValueType.of("varchar(32)"), nameCol.getType());
    Assertions.assertEquals(ValueString.of("utf8mb4"), nameCol.getCharset());
    Assertions.assertEquals(ValueString.of("utf8mb4_general_ci"), nameCol.getCollate());
    Assertions.assertEquals(ValueString.of("'N'"), nameCol.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.FALSE, nameCol.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, nameCol.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of("'name'"), nameCol.getComment());

    // ====== TableIndex: PRIMARY KEY ======
    Iterator<TableIndex> idxIt = table.getTableIndexes().iterator();
    TableIndex pk = idxIt.next();
    Assertions.assertEquals(ValueName.of(TableIndex.PRIMARY_KEY_NAME), pk.getName());
    Assertions.assertEquals(IndexType.PRIMARY, pk.getIndexType());
    Assertions.assertEquals(ValueComment.of(), pk.getComment());
    Assertions.assertEquals(1, pk.getTableIndexColumns().getTableIndexColumnList().size());
    Assertions.assertEquals(ValueName.of("`id`"), pk.getTableIndexColumns().getTableIndexColumnList().get(0).getName());
    Assertions.assertEquals(ValueOrder.of(), pk.getTableIndexColumns().getTableIndexColumnList().get(0).getOrder());

    // ====== TableIndex: UNIQUE KEY ======
    TableIndex uk = idxIt.next();
    Assertions.assertEquals(ValueName.of("`idx_name`"), uk.getName());
    Assertions.assertEquals(IndexType.UNIQUE, uk.getIndexType());
    Assertions.assertEquals(ValueComment.of(), uk.getComment());
    Assertions.assertEquals(1, uk.getTableIndexColumns().getTableIndexColumnList().size());
    Assertions.assertEquals(ValueName.of("`name`"), uk.getTableIndexColumns().getTableIndexColumnList().get(0).getName());
    Assertions.assertEquals(ValueOrder.of(), uk.getTableIndexColumns().getTableIndexColumnList().get(0).getOrder());

    // ====== TableIndex: SIMPLE KEY with comment ======
    TableIndex sk = idxIt.next();
    Assertions.assertEquals(ValueName.of("`idx_id_name`"), sk.getName());
    Assertions.assertEquals(IndexType.SIMPLE, sk.getIndexType());
    Assertions.assertEquals(ValueComment.of("'index comment'"), sk.getComment());
    Assertions.assertEquals(2, sk.getTableIndexColumns().getTableIndexColumnList().size());
    Assertions.assertEquals(ValueName.of("`id`"), sk.getTableIndexColumns().getTableIndexColumnList().get(0).getName());
    Assertions.assertEquals(ValueOrder.of(), sk.getTableIndexColumns().getTableIndexColumnList().get(0).getOrder());
    Assertions.assertEquals(ValueName.of("`name`"), sk.getTableIndexColumns().getTableIndexColumnList().get(1).getName());
    Assertions.assertEquals(ValueOrder.of("desc"), sk.getTableIndexColumns().getTableIndexColumnList().get(1).getOrder());
  }

  // ================================================================
  //              边界 & 分支覆盖
  // ================================================================

  @Test
  void testParseTypeWithArgs() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (c1 decimal(10,2), c2 varchar(255), c3 int)");

    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of(), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of(), table.getComment());
    Assertions.assertEquals(3, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());

    Iterator<TableColumn> it = table.getTableColumns().iterator();
    TableColumn c1 = it.next();
    Assertions.assertEquals(ValueName.of("c1"), c1.getName());
    Assertions.assertEquals(ValueType.of("decimal(10,2)"), c1.getType());
    Assertions.assertEquals(ValueString.of(), c1.getCharset());
    Assertions.assertEquals(ValueString.of(), c1.getCollate());
    Assertions.assertEquals(ValueString.of(), c1.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c1.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c1.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c1.getComment());

    TableColumn c2 = it.next();
    Assertions.assertEquals(ValueName.of("c2"), c2.getName());
    Assertions.assertEquals(ValueType.of("varchar(255)"), c2.getType());
    Assertions.assertEquals(ValueString.of(), c2.getCharset());
    Assertions.assertEquals(ValueString.of(), c2.getCollate());
    Assertions.assertEquals(ValueString.of(), c2.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c2.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c2.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c2.getComment());

    TableColumn c3 = it.next();
    Assertions.assertEquals(ValueName.of("c3"), c3.getName());
    Assertions.assertEquals(ValueType.of("int"), c3.getType());
    Assertions.assertEquals(ValueString.of(), c3.getCharset());
    Assertions.assertEquals(ValueString.of(), c3.getCollate());
    Assertions.assertEquals(ValueString.of(), c3.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c3.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c3.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c3.getComment());
  }

  @Test
  void testParseInvalidDdl() {
    TableParser tableParser = new TableParser();
    Assertions.assertThrows(Exception.class, () -> tableParser.parse("NOT A VALID SQL"));
  }

  @Test
  void testParseNonCreateTableDdl() {
    TableParser tableParser = new TableParser();
    Assertions.assertThrows(ClassCastException.class,
            () -> tableParser.parse("SELECT * FROM t"));
  }

  @Test
  void testParseNotFollowedByNonNull() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int NOT SOMETHING)");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of(), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of(), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());

    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), col.getName());
    Assertions.assertEquals(ValueType.of("int"), col.getType());
    Assertions.assertEquals(ValueString.of(), col.getCharset());
    Assertions.assertEquals(ValueString.of(), col.getCollate());
    Assertions.assertEquals(ValueString.of(), col.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, col.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, col.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), col.getComment());
  }

  @Test
  void testParseSpecAtEndOfList() {
    TableParser tableParser = new TableParser();

    Table t1 = tableParser.parse("CREATE TABLE t (id int COLLATE)");
    Assertions.assertEquals(ValueName.of("t"), t1.getName());
    Assertions.assertEquals(ValueString.of(), t1.getEngine());
    Assertions.assertEquals(ValueString.of(), t1.getCharset());
    Assertions.assertEquals(ValueString.of(), t1.getCollate());
    Assertions.assertEquals(ValueComment.of(), t1.getComment());
    Assertions.assertEquals(1, t1.getTableColumns().columnSize());
    Assertions.assertEquals(0, t1.getTableIndexes().indexSize());

    TableColumn c1 = t1.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), c1.getName());
    Assertions.assertEquals(ValueType.of("int"), c1.getType());
    Assertions.assertEquals(ValueString.of(), c1.getCharset());
    Assertions.assertEquals(ValueString.of(), c1.getCollate());
    Assertions.assertEquals(ValueString.of(), c1.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c1.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c1.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c1.getComment());

    Table t2 = tableParser.parse("CREATE TABLE t (id int COMMENT)");
    Assertions.assertEquals(ValueName.of("t"), t2.getName());
    Assertions.assertEquals(ValueString.of(), t2.getEngine());
    Assertions.assertEquals(ValueString.of(), t2.getCharset());
    Assertions.assertEquals(ValueString.of(), t2.getCollate());
    Assertions.assertEquals(ValueComment.of(), t2.getComment());
    Assertions.assertEquals(1, t2.getTableColumns().columnSize());
    Assertions.assertEquals(0, t2.getTableIndexes().indexSize());

    TableColumn c2 = t2.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), c2.getName());
    Assertions.assertEquals(ValueType.of("int"), c2.getType());
    Assertions.assertEquals(ValueString.of(), c2.getCharset());
    Assertions.assertEquals(ValueString.of(), c2.getCollate());
    Assertions.assertEquals(ValueString.of(), c2.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c2.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c2.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c2.getComment());

    Table t3 = tableParser.parse("CREATE TABLE t (id int DEFAULT)");
    Assertions.assertEquals(ValueName.of("t"), t3.getName());
    Assertions.assertEquals(ValueString.of(), t3.getEngine());
    Assertions.assertEquals(ValueString.of(), t3.getCharset());
    Assertions.assertEquals(ValueString.of(), t3.getCollate());
    Assertions.assertEquals(ValueComment.of(), t3.getComment());
    Assertions.assertEquals(1, t3.getTableColumns().columnSize());
    Assertions.assertEquals(0, t3.getTableIndexes().indexSize());

    TableColumn c3 = t3.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), c3.getName());
    Assertions.assertEquals(ValueType.of("int"), c3.getType());
    Assertions.assertEquals(ValueString.of(), c3.getCharset());
    Assertions.assertEquals(ValueString.of(), c3.getCollate());
    Assertions.assertEquals(ValueString.of(), c3.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c3.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c3.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c3.getComment());
  }

  @Test
  void testParseTableNameVariants() {
    TableParser tableParser = new TableParser();

    Table t1 = tableParser.parse("CREATE TABLE `my_table` (id int)");
    Assertions.assertEquals(ValueName.of("`my_table`"), t1.getName());
    Assertions.assertEquals(ValueString.of(), t1.getEngine());
    Assertions.assertEquals(ValueString.of(), t1.getCharset());
    Assertions.assertEquals(ValueString.of(), t1.getCollate());
    Assertions.assertEquals(ValueComment.of(), t1.getComment());
    Assertions.assertEquals(1, t1.getTableColumns().columnSize());
    Assertions.assertEquals(0, t1.getTableIndexes().indexSize());

    TableColumn c1 = t1.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), c1.getName());
    Assertions.assertEquals(ValueType.of("int"), c1.getType());
    Assertions.assertEquals(ValueString.of(), c1.getCharset());
    Assertions.assertEquals(ValueString.of(), c1.getCollate());
    Assertions.assertEquals(ValueString.of(), c1.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c1.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c1.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c1.getComment());

    Table t2 = tableParser.parse("CREATE TABLE mydb.`mytable` (id int)");
    Assertions.assertEquals(ValueName.of("mydb.`mytable`"), t2.getName());
    Assertions.assertEquals(ValueString.of(), t2.getEngine());
    Assertions.assertEquals(ValueString.of(), t2.getCharset());
    Assertions.assertEquals(ValueString.of(), t2.getCollate());
    Assertions.assertEquals(ValueComment.of(), t2.getComment());
    Assertions.assertEquals(1, t2.getTableColumns().columnSize());
    Assertions.assertEquals(0, t2.getTableIndexes().indexSize());

    TableColumn c2 = t2.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), c2.getName());
    Assertions.assertEquals(ValueType.of("int"), c2.getType());
    Assertions.assertEquals(ValueString.of(), c2.getCharset());
    Assertions.assertEquals(ValueString.of(), c2.getCollate());
    Assertions.assertEquals(ValueString.of(), c2.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c2.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c2.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c2.getComment());
  }

  @Test
  void testParsePartialTableOptions() {
    TableParser tableParser = new TableParser();

    Table t1 = tableParser.parse("CREATE TABLE t (id int) ENGINE=InnoDB");
    Assertions.assertEquals(ValueName.of("t"), t1.getName());
    Assertions.assertEquals(ValueString.of("InnoDB"), t1.getEngine());
    Assertions.assertEquals(ValueString.of(), t1.getCharset());
    Assertions.assertEquals(ValueString.of(), t1.getCollate());
    Assertions.assertEquals(ValueComment.of(), t1.getComment());
    Assertions.assertEquals(1, t1.getTableColumns().columnSize());
    Assertions.assertEquals(0, t1.getTableIndexes().indexSize());

    TableColumn c1 = t1.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), c1.getName());
    Assertions.assertEquals(ValueType.of("int"), c1.getType());
    Assertions.assertEquals(ValueString.of(), c1.getCharset());
    Assertions.assertEquals(ValueString.of(), c1.getCollate());
    Assertions.assertEquals(ValueString.of(), c1.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c1.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c1.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c1.getComment());

    Table t2 = tableParser.parse("CREATE TABLE t (id int) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='test'");
    Assertions.assertEquals(ValueName.of("t"), t2.getName());
    Assertions.assertEquals(ValueString.of("InnoDB"), t2.getEngine());
    Assertions.assertEquals(ValueString.of("utf8mb4"), t2.getCharset());
    Assertions.assertEquals(ValueString.of("utf8mb4_general_ci"), t2.getCollate());
    Assertions.assertEquals(ValueComment.of("'test'"), t2.getComment());
    Assertions.assertEquals(1, t2.getTableColumns().columnSize());
    Assertions.assertEquals(0, t2.getTableIndexes().indexSize());

    TableColumn c2 = t2.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), c2.getName());
    Assertions.assertEquals(ValueType.of("int"), c2.getType());
    Assertions.assertEquals(ValueString.of(), c2.getCharset());
    Assertions.assertEquals(ValueString.of(), c2.getCollate());
    Assertions.assertEquals(ValueString.of(), c2.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, c2.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, c2.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), c2.getComment());
  }

  @Test
  void testParseCharSetNoCollate() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse(
            "CREATE TABLE t (name varchar(32) CHARACTER SET utf8mb4 NOT NULL)");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of(), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of(), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());

    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("name"), col.getName());
    Assertions.assertEquals(ValueType.of("varchar(32)"), col.getType());
    Assertions.assertEquals(ValueString.of("utf8mb4"), col.getCharset());
    Assertions.assertEquals(ValueString.of(), col.getCollate());
    Assertions.assertEquals(ValueString.of(), col.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.FALSE, col.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, col.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), col.getComment());
  }

  @Test
  void testParseDefaultNull() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int DEFAULT NULL)");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of(), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of(), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());

    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), col.getName());
    Assertions.assertEquals(ValueType.of("int"), col.getType());
    Assertions.assertEquals(ValueString.of(), col.getCharset());
    Assertions.assertEquals(ValueString.of(), col.getCollate());
    Assertions.assertEquals(ValueString.of(), col.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, col.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, col.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), col.getComment());
  }

  @Test
  void testParseDefaultMultiToken() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse(
            "CREATE TABLE t (id int, created_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of(), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of(), table.getComment());
    Assertions.assertEquals(2, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());

    Iterator<TableColumn> it = table.getTableColumns().iterator();
    TableColumn col1 = it.next();
    Assertions.assertEquals(ValueName.of("id"), col1.getName());
    Assertions.assertEquals(ValueType.of("int"), col1.getType());
    Assertions.assertEquals(ValueString.of(), col1.getCharset());
    Assertions.assertEquals(ValueString.of(), col1.getCollate());
    Assertions.assertEquals(ValueString.of(), col1.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, col1.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, col1.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), col1.getComment());

    TableColumn col2 = it.next();
    Assertions.assertEquals(ValueName.of("created_at"), col2.getName());
    Assertions.assertEquals(ValueType.of("datetime"), col2.getType());
    Assertions.assertEquals(ValueString.of(), col2.getCharset());
    Assertions.assertEquals(ValueString.of(), col2.getCollate());
    Assertions.assertNotNull(col2.getDefaultValue().getValue());
    Assertions.assertEquals("CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", col2.getDefaultValue().getValue());
    Assertions.assertEquals(ValueBoolean.TRUE, col2.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, col2.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), col2.getComment());
  }

  @Test
  void testParseIndexNoSpecList() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int, PRIMARY KEY (id))");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of(), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of(), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(1, table.getTableIndexes().indexSize());

    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), col.getName());
    Assertions.assertEquals(ValueType.of("int"), col.getType());

    TableIndex idx = table.getTableIndexes().iterator().next();
    Assertions.assertEquals(ValueName.of(TableIndex.PRIMARY_KEY_NAME), idx.getName());
    Assertions.assertEquals(IndexType.PRIMARY, idx.getIndexType());
    Assertions.assertEquals(ValueComment.of(), idx.getComment());
    Assertions.assertEquals(1, idx.getTableIndexColumns().getTableIndexColumnList().size());
    Assertions.assertEquals(ValueName.of("id"), idx.getTableIndexColumns().getTableIndexColumnList().get(0).getName());
    Assertions.assertEquals(ValueOrder.of(), idx.getTableIndexColumns().getTableIndexColumnList().get(0).getOrder());
  }

  @Test
  void testParseIndexWithOnlyCommentSpec() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int, KEY idx (id) COMMENT 'test comment')");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of(), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of(), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(1, table.getTableIndexes().indexSize());

    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), col.getName());
    Assertions.assertEquals(ValueType.of("int"), col.getType());

    TableIndex idx = table.getTableIndexes().iterator().next();
    Assertions.assertEquals(ValueName.of("idx"), idx.getName());
    Assertions.assertEquals(IndexType.SIMPLE, idx.getIndexType());
    Assertions.assertEquals(ValueComment.of("'test comment'"), idx.getComment());
    Assertions.assertEquals(1, idx.getTableIndexColumns().getTableIndexColumnList().size());
    Assertions.assertEquals(ValueName.of("id"), idx.getTableIndexColumns().getTableIndexColumnList().get(0).getName());
    Assertions.assertEquals(ValueOrder.of(), idx.getTableIndexColumns().getTableIndexColumnList().get(0).getOrder());
  }

  @Test
  void testParseNotAtEndOfSpecList() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int NOT)");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());

    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), col.getName());
    Assertions.assertEquals(ValueType.of("int"), col.getType());
    Assertions.assertEquals(ValueString.of(), col.getCharset());
    Assertions.assertEquals(ValueString.of(), col.getCollate());
    Assertions.assertEquals(ValueString.of(), col.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, col.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, col.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), col.getComment());
  }

  @Test
  void testParseDefaultWithoutComment() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int DEFAULT 'val')");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());

    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), col.getName());
    Assertions.assertEquals(ValueType.of("int"), col.getType());
    Assertions.assertEquals(ValueString.of(), col.getCharset());
    Assertions.assertEquals(ValueString.of(), col.getCollate());
    Assertions.assertEquals(ValueString.of("'val'"), col.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.TRUE, col.getIfNullable());
    Assertions.assertEquals(ValueBoolean.FALSE, col.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of(), col.getComment());
  }

  @Test
  void testParseExtraTableOptions() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=64 COMMENT='t'");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of("InnoDB"), table.getEngine());
    Assertions.assertEquals(ValueString.of("utf8"), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of("'t'"), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());
  }

  @Test
  void testParseTableOptionCaseInsensitive() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int) engine=InnoDB default charset=utf8 comment='test'");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of("InnoDB"), table.getEngine());
    Assertions.assertEquals(ValueString.of("utf8"), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of("'test'"), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());
  }

  @Test
  void testParseColumnSpecsDifferentOrder() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id bigint COMMENT 'pk' NOT NULL AUTO_INCREMENT DEFAULT 0)");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());

    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), col.getName());
    Assertions.assertEquals(ValueType.of("bigint"), col.getType());
    Assertions.assertEquals(ValueString.of(), col.getCharset());
    Assertions.assertEquals(ValueString.of(), col.getCollate());
    Assertions.assertEquals(ValueString.of("0"), col.getDefaultValue());
    Assertions.assertEquals(ValueBoolean.FALSE, col.getIfNullable());
    Assertions.assertEquals(ValueBoolean.TRUE, col.getIfAutoIncrement());
    Assertions.assertEquals(ValueComment.of("'pk'"), col.getComment());
  }

  @Test
  void testParseMultipleAutoIncrementColumns() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id bigint NOT NULL AUTO_INCREMENT, name varchar(32) NOT NULL AUTO_INCREMENT)");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(2, table.getTableColumns().columnSize());

    Iterator<TableColumn> it = table.getTableColumns().iterator();
    TableColumn c1 = it.next();
    Assertions.assertEquals(ValueName.of("id"), c1.getName());
    Assertions.assertEquals(ValueType.of("bigint"), c1.getType());
    Assertions.assertEquals(ValueBoolean.FALSE, c1.getIfNullable());
    Assertions.assertEquals(ValueBoolean.TRUE, c1.getIfAutoIncrement());

    TableColumn c2 = it.next();
    Assertions.assertEquals(ValueName.of("name"), c2.getName());
    Assertions.assertEquals(ValueType.of("varchar(32)"), c2.getType());
    Assertions.assertEquals(ValueBoolean.FALSE, c2.getIfNullable());
    Assertions.assertEquals(ValueBoolean.TRUE, c2.getIfAutoIncrement());
  }

  @Test
  void testParseDefaultWithComment() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int DEFAULT 'val' COMMENT 'c')");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());

    TableColumn col = table.getTableColumns().iterator().next();
    Assertions.assertEquals(ValueName.of("id"), col.getName());
    Assertions.assertEquals(ValueString.of("'val'"), col.getDefaultValue());
    Assertions.assertEquals(ValueComment.of("'c'"), col.getComment());
    Assertions.assertEquals(ValueBoolean.TRUE, col.getIfNullable());
  }

  @Test
  void testParseCharsetAndCollateTableOptionsOnly() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of("utf8mb4"), table.getCharset());
    Assertions.assertEquals(ValueString.of("utf8mb4_general_ci"), table.getCollate());
    Assertions.assertEquals(ValueComment.of(), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());
  }

  @Test
  void testParseCommentOnlyTableOption() {
    TableParser tableParser = new TableParser();
    Table table = tableParser.parse("CREATE TABLE t (id int) COMMENT='only comment'");
    Assertions.assertEquals(ValueName.of("t"), table.getName());
    Assertions.assertEquals(ValueString.of(), table.getEngine());
    Assertions.assertEquals(ValueString.of(), table.getCharset());
    Assertions.assertEquals(ValueString.of(), table.getCollate());
    Assertions.assertEquals(ValueComment.of("'only comment'"), table.getComment());
    Assertions.assertEquals(1, table.getTableColumns().columnSize());
    Assertions.assertEquals(0, table.getTableIndexes().indexSize());
  }

}
