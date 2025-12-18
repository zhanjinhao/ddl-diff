package cn.addenda.ddldiff.parse.test.parse;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.Table;
import cn.addenda.ddldiff.parse.TableParser;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

}
