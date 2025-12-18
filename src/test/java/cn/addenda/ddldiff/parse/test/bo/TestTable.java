package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.Table;
import cn.addenda.ddldiff.bo.diff.DiffTable;
import cn.addenda.ddldiff.parse.TableParser;
import com.fasterxml.jackson.core.type.TypeReference;
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

}
