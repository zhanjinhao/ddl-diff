package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.Table;
import cn.addenda.ddldiff.bo.ValueName;
import cn.addenda.ddldiff.bo.ValueString;
import cn.addenda.ddldiff.bo.ValueComment;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTable;
import cn.addenda.ddldiff.parse.TableParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTable {

  String ddl1 = "CREATE TABLE `t_closure_table` (\n" +
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

  String ddl2 = "CREATE TABLE `t_closure_table` (\n" +
          "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
          "  `ancestor` bigint NOT NULL,\n" +
          "  `descendant` bigint NOT NULL,\n" +
          "  `distance` int NOT NULL,\n" +
          "  `creator` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `create_time` datetime(3) NOT NULL,\n" +
          "  `modifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `modifier_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `modify_time` datetime(3) NOT NULL,\n" +
          "  PRIMARY KEY (`id`),\n" +
          "  KEY `t_closure_table_down` (`ancestor`,`descendant`,`distance`),\n" +
          "  KEY `t_closure_table_up` (`descendant`,`ancestor`,`distance`)\n" +
          ") ENGINE=InnoDB AUTO_INCREMENT=867 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

  String ddl3 = "CREATE TABLE `t_closure_table` (\n" +
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
          "  KEY `t_closure_table_up` (`descendant`,`ancestor`,`distance`)\n" +
          ") ENGINE=InnoDB AUTO_INCREMENT=867 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

  String ddl4 = "CREATE TABLE `t_closure_table` (\n" +
          "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
          "  `ancestor` bigint NOT NULL,\n" +
          "  `descendant` bigint NOT NULL,\n" +
          "  `distance` int NOT NULL,\n" +
          "  `creator` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `creator_name` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,\n" +
          "  `create_time` datetime(3) NOT NULL,\n" +
          "  `modifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `modifier_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
          "  `modify_time` datetime(3) NOT NULL,\n" +
          "  PRIMARY KEY (`id`),\n" +
          "  KEY `t_closure_table_down` (`ancestor`,`descendant`,`distance`),\n" +
          "  KEY `t_closure_table_up` (`descendant`,`ancestor`,`distance`)\n" +
          ") ENGINE=InnoDB AUTO_INCREMENT=867 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

  String ddl5 = "CREATE TABLE `t_closure_table` (\n" +
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
          "  KEY `t_closure_table_down` (`ancestor`,`descendant`),\n" +
          "  KEY `t_closure_table_up` (`descendant`,`ancestor`,`distance`)\n" +
          ") ENGINE=InnoDB AUTO_INCREMENT=867 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

  String ddl6 = "CREATE TABLE `t_closure_table` (\n" +
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
          "  PRIMARY KEY (`id`, `create_tm`),\n" +
          "  KEY `t_closure_table_down` (`ancestor`,`descendant`,`distance`),\n" +
          "  KEY `t_closure_table_up` (`descendant`,`ancestor`,`distance`)\n" +
          ") ENGINE=InnoDB AUTO_INCREMENT=867 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

  private static final TypeReference<DiffTable> typeReference = new TypeReference<DiffTable>() {
  };

  @Test
  void test1() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table2 = tableParser.parse(ddl2);

    String expected = "{\n" +
            "  \"tableName\": \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableColumns\": [\n" +
            "    {\n" +
            "      \"columnName\": \"`creator_name`(source)-null(target)\",\n" +
            "      \"diffName\": {\n" +
            "        \"source\": \"`creator_name`\",\n" +
            "        \"target\": null\n" +
            "      },\n" +
            "      \"diffType\": {\n" +
            "        \"source\": \"varchar(32)\",\n" +
            "        \"target\": null\n" +
            "      },\n" +
            "      \"diffCharset\": {\n" +
            "        \"source\": \"utf8mb4\",\n" +
            "        \"target\": null\n" +
            "      },\n" +
            "      \"diffCollate\": {\n" +
            "        \"source\": \"utf8mb4_unicode_ci\",\n" +
            "        \"target\": null\n" +
            "      },\n" +
            "      \"diffIfNullable\": {\n" +
            "        \"source\": false,\n" +
            "        \"target\": true\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    DiffTable diffTable = table1.runtimeDiff(table2);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);

    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  @Test
  void test2() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table2 = tableParser.parse(ddl2);

    String expected = "{\n" +
            "  \"tableName\" : \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableColumns\" : [ {\n" +
            "    \"columnName\" : \"null(source)-`creator_name`(target)\",\n" +
            "    \"diffName\" : {\n" +
            "      \"source\" : null,\n" +
            "      \"target\" : \"`creator_name`\"\n" +
            "    },\n" +
            "    \"diffType\" : {\n" +
            "      \"source\" : null,\n" +
            "      \"target\" : \"varchar(32)\"\n" +
            "    },\n" +
            "    \"diffCharset\" : {\n" +
            "      \"source\" : null,\n" +
            "      \"target\" : \"utf8mb4\"\n" +
            "    },\n" +
            "    \"diffCollate\" : {\n" +
            "      \"source\" : null,\n" +
            "      \"target\" : \"utf8mb4_unicode_ci\"\n" +
            "    },\n" +
            "    \"diffIfNullable\" : {\n" +
            "      \"source\" : true,\n" +
            "      \"target\" : false\n" +
            "    }\n" +
            "  } ]\n" +
            "}";

    DiffTable diffTable = table2.runtimeDiff(table1);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);
    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  @Test
  void test3() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table3 = tableParser.parse(ddl3);

    String expected = "{\n" +
            "  \"tableName\": \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableIndexes\": [\n" +
            "    {\n" +
            "      \"indexName\": \"`t_closure_table_down`(source)-null(target)\",\n" +
            "      \"diffTableIndexColumns\": {\n" +
            "        \"source\": \"`ancestor`, `descendant`, `distance`\",\n" +
            "        \"target\": null\n" +
            "      },\n" +
            "      \"diffName\": {\n" +
            "        \"source\": \"`t_closure_table_down`\",\n" +
            "        \"target\": null\n" +
            "      },\n" +
            "      \"diffIndexType\": {\n" +
            "        \"source\": \"SIMPLE\",\n" +
            "        \"target\": null\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    DiffTable diffTable = table1.runtimeDiff(table3);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);
    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  @Test
  void test4() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table3 = tableParser.parse(ddl3);

    String expected = "{\n" +
            "  \"tableName\": \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableIndexes\": [\n" +
            "    {\n" +
            "      \"indexName\": \"null(source)-`t_closure_table_down`(target)\",\n" +
            "      \"diffTableIndexColumns\": {\n" +
            "        \"source\": null,\n" +
            "        \"target\": \"`ancestor`, `descendant`, `distance`\"\n" +
            "      },\n" +
            "      \"diffName\": {\n" +
            "        \"source\": null,\n" +
            "        \"target\": \"`t_closure_table_down`\"\n" +
            "      },\n" +
            "      \"diffIndexType\": {\n" +
            "        \"source\": null,\n" +
            "        \"target\": \"SIMPLE\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    DiffTable diffTable = table3.runtimeDiff(table1);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);
    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  @Test
  void test5() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table4 = tableParser.parse(ddl4);

    String expected = "{\n" +
            "  \"tableName\": \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableColumns\": [\n" +
            "    {\n" +
            "      \"columnName\": \"`creator_name`(source)-`creator_name`(target)\",\n" +
            "      \"diffType\": {\n" +
            "        \"source\": \"varchar(32)\",\n" +
            "        \"target\": \"varchar(16)\"\n" +
            "      },\n" +
            "      \"diffCharset\": {\n" +
            "        \"source\": \"utf8mb4\",\n" +
            "        \"target\": \"utf8mb3\"\n" +
            "      },\n" +
            "      \"diffCollate\": {\n" +
            "        \"source\": \"utf8mb4_unicode_ci\",\n" +
            "        \"target\": \"utf8mb3_unicode_ci\"\n" +
            "      },\n" +
            "      \"diffIfNullable\": {\n" +
            "        \"source\": false,\n" +
            "        \"target\": true\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    DiffTable diffTable = table1.runtimeDiff(table4);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);
    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  @Test
  void test6() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table4 = tableParser.parse(ddl4);

    String expected = "{\n" +
            "  \"tableName\": \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableColumns\": [\n" +
            "    {\n" +
            "      \"columnName\": \"`creator_name`(source)-`creator_name`(target)\",\n" +
            "      \"diffType\": {\n" +
            "        \"source\": \"varchar(16)\",\n" +
            "        \"target\": \"varchar(32)\"\n" +
            "      },\n" +
            "      \"diffCharset\": {\n" +
            "        \"source\": \"utf8mb3\",\n" +
            "        \"target\": \"utf8mb4\"\n" +
            "      },\n" +
            "      \"diffCollate\": {\n" +
            "        \"source\": \"utf8mb3_unicode_ci\",\n" +
            "        \"target\": \"utf8mb4_unicode_ci\"\n" +
            "      },\n" +
            "      \"diffIfNullable\": {\n" +
            "        \"source\": true,\n" +
            "        \"target\": false\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    DiffTable diffTable = table4.runtimeDiff(table1);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);
    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  @Test
  void test7() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table5 = tableParser.parse(ddl5);

    String expected = "{\n" +
            "  \"tableName\": \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableIndexes\": [\n" +
            "    {\n" +
            "      \"indexName\": \"`t_closure_table_down`(source)-`t_closure_table_down`(target)\",\n" +
            "      \"diffTableIndexColumns\": {\n" +
            "        \"source\": \"`ancestor`, `descendant`, `distance`\",\n" +
            "        \"target\": \"`ancestor`, `descendant`\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    DiffTable diffTable = table1.runtimeDiff(table5);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);
    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  @Test
  void test8() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table5 = tableParser.parse(ddl5);

    String expected = "{\n" +
            "  \"tableName\": \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableIndexes\": [\n" +
            "    {\n" +
            "      \"indexName\": \"`t_closure_table_down`(source)-`t_closure_table_down`(target)\",\n" +
            "      \"diffTableIndexColumns\": {\n" +
            "        \"source\": \"`ancestor`, `descendant`\",\n" +
            "        \"target\": \"`ancestor`, `descendant`, `distance`\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    DiffTable diffTable = table5.runtimeDiff(table1);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);
    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  @Test
  void test9() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table6 = tableParser.parse(ddl6);

    String expected = "{\n" +
            "  \"tableName\" : \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableIndexes\" : [ {\n" +
            "    \"indexName\" : \"_PRIMARY_KEY_(source)-_PRIMARY_KEY_(target)\",\n" +
            "    \"diffTableIndexColumns\" : {\n" +
            "      \"source\" : \"`id`\",\n" +
            "      \"target\" : \"`id`, `create_tm`\"\n" +
            "    }\n" +
            "  } ]\n" +
            "}";

    DiffTable diffTable = table1.runtimeDiff(table6);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);
    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  @Test
  void test10() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table6 = tableParser.parse(ddl6);

    String expected = "{\n" +
            "  \"tableName\" : \"`t_closure_table`(source)-`t_closure_table`(target)\",\n" +
            "  \"diffTableIndexes\" : [ {\n" +
            "    \"indexName\" : \"_PRIMARY_KEY_(source)-_PRIMARY_KEY_(target)\",\n" +
            "    \"diffTableIndexColumns\" : {\n" +
            "      \"source\" : \"`id`, `create_tm`\",\n" +
            "      \"target\" : \"`id`\"\n" +
            "    }\n" +
            "  } ]\n" +
            "}";

    DiffTable diffTable = table6.runtimeDiff(table1);
    Assertions.assertEquals(JacksonUtils.formatJson(expected), JacksonUtils.formatJson(diffTable.diff()));

    DiffTable diffTable2 = JacksonUtils.toObj(expected, typeReference);
    Assertions.assertEquals(diffTable.diff(), diffTable2.diff());
  }

  Table NULL = Table.of(null, null, null, null, null, null, null);

  // ================================================================
  //              Table 对象方法 (equals/hashCode/deepClone)
  // ================================================================

  @Test
  void testEqualsAndHashCode() {
    TableParser tableParser = new TableParser();
    Table table1a = tableParser.parse(ddl1);
    Table table1b = tableParser.parse(ddl1);
    Table table2 = tableParser.parse(ddl2);

    Assertions.assertEquals(table1a, table1b);
    Assertions.assertEquals(table1a.hashCode(), table1b.hashCode());
    Assertions.assertNotEquals(table1a, table2);
    Assertions.assertNotEquals(table1a, NULL);
  }

  @Test
  void testDeepClone() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table cloned = table1.deepClone();

    Assertions.assertEquals(table1, cloned);
    Assertions.assertNotSame(table1, cloned);
    Assertions.assertNotSame(table1.getName(), cloned.getName());
    Assertions.assertNotSame(table1.getTableColumns(), cloned.getTableColumns());
    Assertions.assertNotSame(table1.getTableIndexes(), cloned.getTableIndexes());
  }

  // ================================================================
  //              Table.equals 变体 (absolutelyEquals/runtimeEquals)
  // ================================================================

  @Test
  void testAbsolutelyEquals() {
    TableParser tableParser = new TableParser();
    Table table1a = tableParser.parse(ddl1);
    Table table1b = tableParser.parse(ddl1);
    Table table2 = tableParser.parse(ddl2);

    Assertions.assertTrue(table1a.absolutelyEquals(table1b));
    Assertions.assertFalse(table1a.absolutelyEquals(table2));
    Assertions.assertTrue(NULL
            .absolutelyEquals(NULL));
  }

  @Test
  void testRuntimeEquals() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table2 = tableParser.parse(ddl2);

    Assertions.assertTrue(table1.runtimeEquals(table1));
    Assertions.assertFalse(table1.runtimeEquals(table2));
    Assertions.assertTrue(NULL
            .runtimeEquals(NULL));
    Assertions.assertTrue(NULL.runtimeEquals(null));
  }

  // ================================================================
  //              diff 两个相同表 => EQUALS
  // ================================================================

  @Test
  void testAbsolutelyDiffIdentical() {
    TableParser tableParser = new TableParser();
    Table table1a = tableParser.parse(ddl1);
    Table table1b = tableParser.parse(ddl1);

    DiffTable diff = table1a.absolutelyDiff(table1b);
    Assertions.assertEquals(Diff.EQUALS, diff.diff());
    Assertions.assertTrue(DiffTable.ifNull(diff));
  }

  @Test
  void testRuntimeDiffIdentical() {
    TableParser tableParser = new TableParser();
    Table table1a = tableParser.parse(ddl1);
    Table table1b = tableParser.parse(ddl1);

    DiffTable diff = table1a.runtimeDiff(table1b);
    Assertions.assertEquals(Diff.EQUALS, diff.diff());
    Assertions.assertTrue(DiffTable.ifNull(diff));
  }

  // ================================================================
  //              diffWithNull
  // ================================================================

  @Test
  void testDiffWithNull() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);

    DiffTable absolutelyDiff = table1.absolutelyDiff(null);
    Assertions.assertNotNull(absolutelyDiff);
    Assertions.assertFalse(DiffTable.ifNull(absolutelyDiff));
    Assertions.assertNotEquals(Diff.EQUALS, absolutelyDiff.diff());

    DiffTable runtimeDiff = table1.runtimeDiff(null);
    Assertions.assertNotNull(runtimeDiff);
    Assertions.assertFalse(DiffTable.ifNull(runtimeDiff));
    Assertions.assertNotEquals(Diff.EQUALS, runtimeDiff.diff());

    Table empty = NULL;
    DiffTable emptyNullDiff = empty.absolutelyDiff(null);
    Assertions.assertEquals(Diff.EQUALS, emptyNullDiff.diff());

    DiffTable emptyNullRuntime = empty.runtimeDiff(null);
    Assertions.assertEquals(Diff.EQUALS, emptyNullRuntime.diff());
  }

  // ================================================================
  //              testConsistency
  // ================================================================

  @Test
  void testConsistency() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table2 = tableParser.parse(ddl2);

    Assertions.assertTrue(table1.runtimeEquals(table1));
    Assertions.assertEquals(Diff.EQUALS, table1.runtimeDiff(table1).diff());
    Assertions.assertTrue(table1.absolutelyEquals(table1));
    Assertions.assertEquals(Diff.EQUALS, table1.absolutelyDiff(table1).diff());

    Assertions.assertFalse(table1.runtimeEquals(table2));
    Assertions.assertNotEquals(Diff.EQUALS, table1.runtimeDiff(table2).diff());
    Assertions.assertFalse(table1.absolutelyEquals(table2));
    Assertions.assertNotEquals(Diff.EQUALS, table1.absolutelyDiff(table2).diff());

    Table empty = NULL;
    Assertions.assertTrue(empty.runtimeEquals(empty));
    Assertions.assertEquals(Diff.EQUALS, empty.runtimeDiff(empty).diff());
    Assertions.assertTrue(empty.absolutelyEquals(empty));
    Assertions.assertEquals(Diff.EQUALS, empty.absolutelyDiff(empty).diff());

    Assertions.assertTrue(empty.runtimeEquals(null));
    Assertions.assertFalse(empty.absolutelyEquals(null));
  }

  // ================================================================
  //              Table of / builder
  // ================================================================

  @Test
  void testOf() {
    Table table = Table.of(
            ValueName.of("`t`"), null, null,
            ValueString.of("InnoDB"), null, null,
            ValueComment.of("'c'"));

    Assertions.assertEquals(ValueName.of("`t`"), table.getName());
    Assertions.assertEquals(ValueString.of("InnoDB"), table.getEngine());
    Assertions.assertEquals(ValueComment.of("'c'"), table.getComment());
    Assertions.assertEquals(NULL.getTableColumns(),
            table.getTableColumns());
  }

  @Test
  void testBuilder() {
    Table table = Table.builder()
            .name(ValueName.of("`t`"))
            .engine(ValueString.of("InnoDB"))
            .charset(ValueString.of("utf8mb4"))
            .comment(ValueComment.of("'c'"))
            .build();

    Assertions.assertEquals(ValueName.of("`t`"), table.getName());
    Assertions.assertEquals(ValueString.of("InnoDB"), table.getEngine());
    Assertions.assertEquals(ValueString.of("utf8mb4"), table.getCharset());
    Assertions.assertEquals(ValueComment.of("'c'"), table.getComment());
  }

  // ================================================================
  //              Table 序列化 / 反序列化 null
  // ================================================================

  @Test
  void testTableSerializeNull() {
    String json = JacksonUtils.toStr((Table) null);
    Assertions.assertNull(json);
  }

  @Test
  void testTableDeserializeNull() {
    Table result = JacksonUtils.toObj("null", new TypeReference<Table>() {
    });
    Assertions.assertEquals(NULL, result);

    Table result2 = JacksonUtils.toObj("\"\"", new TypeReference<Table>() {
    });
    Assertions.assertEquals(NULL, result2);
  }

  // ================================================================
  //              Table 序列化 / 反序列化 round-trip
  // ================================================================

  @Test
  void testTableSerializeDeserializeRoundTrip() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    String json = JacksonUtils.toStr(table1);
    Table restored = JacksonUtils.toObj(json, new TypeReference<Table>() {
    });
    Assertions.assertEquals(table1, restored);
  }

  // ================================================================
  //              DiffTable 序列化 / 反序列化 round-trip
  // ================================================================

  @Test
  void testDiffTableSerializeDeserializeRoundTrip() {
    TableParser tableParser = new TableParser();
    Table table1 = tableParser.parse(ddl1);
    Table table4 = tableParser.parse(ddl4);
    DiffTable original = table1.runtimeDiff(table4);
    String json = original.diff();
    Assertions.assertNotNull(json);
    Assertions.assertNotEquals(Diff.EQUALS, json);

    DiffTable restored = JacksonUtils.toObj(json, typeReference);
    Assertions.assertEquals(original, restored);
  }

  @Test
  void testDeserializeEqualsRoundTrip() {
    Assertions.assertEquals(Diff.EQUALS, DiffTable.of(null, null, null, null, null, null, null, null).diff());
    DiffTable restored = JacksonUtils.toObj(Diff.EQUALS, new TypeReference<DiffTable>() {
    });
    Assertions.assertTrue(DiffTable.ifNull(restored));
  }

  @Test
  void testDeserializeInvalidStringThrows() {
    MismatchedInputException e = Assertions.assertThrows(MismatchedInputException.class,
            () -> JacksonUtils.toObj("\"foobar\"", new TypeReference<DiffTable>() {
            }));
    Assertions.assertTrue(e.getMessage().contains("Can not deserialize \"foobar\" to class cn.addenda.ddldiff.bo.diff.DiffTable"));
  }

}
