package cn.addenda.ddldiff.parse.test;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.junit.jupiter.api.Test;

class JsqlparserTest {

  String ddl = "CREATE TABLE `a.t_flight_pln_detail1` (\n" +
          "  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n" +
          "  `FLIGHT_ID` bigint DEFAULT NULL COMMENT '航班ID',\n" +
          "  `FLIGHT_DATE` datetime NOT NULL COMMENT '航班日期',\n" +
          "  `FLIGHT_NO` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '航班号',\n" +
          "  `DEPARTURE_AIRPORT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '起飞机场',\n" +
          "  `ARRIVAL_AIRPORT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '着陆机场',\n" +
          "  `PTD` datetime DEFAULT NULL COMMENT '局批时间',\n" +
          "  `STD` datetime DEFAULT NULL COMMENT '计飞时间',\n" +
          "  `DEP_SEND_FLAG` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '起飞机场是否已发',\n" +
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
          "  KEY `IDX_FLIGHT_PLN_DETAIL` (`FLIGHT_ID`),\n" +
          "  KEY `idx` (`FLIGHT_ID`,`FLIGHT_NO` desc)\n" +
          ") ENGINE=InnoDB AUTO_INCREMENT=377979 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='航班PLN发送明细'\n";

  @Test
  void test1() throws JSQLParserException {
    CreateTable parse = (CreateTable) CCJSqlParserUtil.parse(ddl);
    System.out.println(parse);
  }



}
