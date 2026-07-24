package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.*;
import cn.addenda.ddldiff.bo.diff.ComparedKey;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTableColumn;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableColumn {

  TableColumn source = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target1 = TableColumn.builder()
          .name(ValueName.of(" `AGE` "))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target2 = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of(" INT "))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target3 = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb3"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target4 = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb3_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target5 = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("01"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target6 = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.TRUE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target7 = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.TRUE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target8 = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄1"))
          .build();

  TableColumn targetN = TableColumn.builder()
          .name(ValueName.of("name"))
          .type(ValueType.of("varchar(20)"))
          .charset(ValueString.of("utf8mb3"))
          .collate(ValueString.of("utf8mb3_general_ci"))
          .ifNullable(ValueBoolean.TRUE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("姓名"))
          .build();

  @Test
  void test1() {
    Assertions.assertEquals(true, source.runtimeEquals(source));
    Assertions.assertEquals(true, source.runtimeEquals(target1));
    Assertions.assertEquals(true, source.runtimeEquals(target2));
    Assertions.assertEquals(false, source.runtimeEquals(target3));
    Assertions.assertEquals(false, source.runtimeEquals(target4));
    Assertions.assertEquals(false, source.runtimeEquals(target5));
    Assertions.assertEquals(false, source.runtimeEquals(target6));
    Assertions.assertEquals(false, source.runtimeEquals(target7));
    Assertions.assertEquals(true, source.runtimeEquals(target8));
    Assertions.assertEquals(false, source.runtimeEquals(targetN));
  }

  @Test
  void test2() {
    Assertions.assertEquals(true, source.absolutelyEquals(source));
    Assertions.assertEquals(false, source.absolutelyEquals(target1));
    Assertions.assertEquals(false, source.absolutelyEquals(target2));
    Assertions.assertEquals(false, source.absolutelyEquals(target3));
    Assertions.assertEquals(false, source.absolutelyEquals(target4));
    Assertions.assertEquals(false, source.absolutelyEquals(target5));
    Assertions.assertEquals(false, source.absolutelyEquals(target6));
    Assertions.assertEquals(false, source.absolutelyEquals(target7));
    Assertions.assertEquals(false, source.absolutelyEquals(target8));
    Assertions.assertEquals(false, source.absolutelyEquals(targetN));
  }

  @Test
  void test3() {
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target2).diff());
    System.out.println(source.runtimeDiff(target3).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target3.getName()),
                    null, null, source.getCharset().runtimeDiff(target3.getCharset()), null,
                    null, null, null, null).diff(),
            source.runtimeDiff(target3).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target4.getName()),
                    null, null, null, source.getCollate().runtimeDiff(target4.getCollate()),
                    null, null, null, null).diff(),
            source.runtimeDiff(target4).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target5.getName()),
                    null, null, null, null,
                    source.getDefaultValue().runtimeDiff(target5.getDefaultValue()), null, null, null).diff(),
            source.runtimeDiff(target5).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target6.getName()),
                    null, null, null, null,
                    null, source.getIfNullable().runtimeDiff(target6.getIfNullable()), null, null).diff(),
            source.runtimeDiff(target6).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target7.getName()),
                    null, null, null, null,
                    null, null, source.getIfAutoIncrement().runtimeDiff(target7.getIfAutoIncrement()), null).diff(),
            source.runtimeDiff(target7).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target8.getName()),
                    null, null, null, null,
                    null, null, null, source.getComment().runtimeDiff(target8.getComment())).diff(),
            source.runtimeDiff(target8).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), targetN.getName()),
                    source.getName().runtimeDiff(targetN.getName()), source.getType().runtimeDiff(targetN.getType()),
                    source.getCharset().runtimeDiff(targetN.getCharset()), source.getCollate().runtimeDiff(targetN.getCollate()),
                    source.getDefaultValue().runtimeDiff(targetN.getDefaultValue()), source.getIfNullable().runtimeDiff(targetN.getIfNullable()),
                    source.getIfAutoIncrement().runtimeDiff(targetN.getIfAutoIncrement()), source.getComment().runtimeDiff(targetN.getComment())).diff(),
            source.runtimeDiff(targetN).diff());
    System.out.println(source.runtimeDiff(targetN).diff());
  }

  @Test
  void test4() {
    Assertions.assertEquals(Diff.EQUALS, source.absolutelyDiff(source).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target1.getName()),
                    source.getName().absolutelyDiff(target1.getName()), null, null, null,
                    null, null, null, null).diff(),
            source.absolutelyDiff(target1).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target2.getName()),
                    null, source.getType().absolutelyDiff(target2.getType()), null, null,
                    null, null, null, null).diff(),
            source.absolutelyDiff(target2).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target3.getName()),
                    null, null, source.getCharset().absolutelyDiff(target3.getCharset()), null,
                    null, null, null, null).diff(),
            source.absolutelyDiff(target3).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target4.getName()),
                    null, null, null, source.getCollate().absolutelyDiff(target4.getCollate()),
                    null, null, null, null).diff(),
            source.absolutelyDiff(target4).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target5.getName()),
                    null, null, null, null,
                    source.getDefaultValue().absolutelyDiff(target5.getDefaultValue()), null, null, null).diff(),
            source.absolutelyDiff(target5).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target6.getName()),
                    null, null, null, null,
                    null, source.getIfNullable().absolutelyDiff(target6.getIfNullable()), null, null).diff(),
            source.absolutelyDiff(target6).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target7.getName()),
                    null, null, null, null,
                    null, null, source.getIfAutoIncrement().absolutelyDiff(target7.getIfAutoIncrement()), null).diff(),
            source.absolutelyDiff(target7).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), target8.getName()),
                    null, null, null, null,
                    null, null, null, source.getComment().absolutelyDiff(target8.getComment())).diff(),
            source.absolutelyDiff(target8).diff());
    Assertions.assertEquals(
            DiffTableColumn.of(ComparedKey.of(source.getName(), targetN.getName()),
                    source.getName().absolutelyDiff(targetN.getName()), source.getType().absolutelyDiff(targetN.getType()),
                    source.getCharset().absolutelyDiff(targetN.getCharset()), source.getCollate().absolutelyDiff(targetN.getCollate()),
                    source.getDefaultValue().absolutelyDiff(targetN.getDefaultValue()), source.getIfNullable().absolutelyDiff(targetN.getIfNullable()),
                    source.getIfAutoIncrement().absolutelyDiff(targetN.getIfAutoIncrement()), source.getComment().absolutelyDiff(targetN.getComment())).diff(),
            source.absolutelyDiff(targetN).diff());
  }

  @Test
  void test5() {
    EnvContext.set("uat", "pro");
    try {
      String diff = source.absolutelyDiff(targetN).diff();
//      System.out.println(diff);
      Assertions.assertEquals("{\"columnName\":\"age(uat)-name(pro)\",\"diffName\":{\"uat\":\"age\",\"pro\":\"name\"},\"diffType\":{\"uat\":\"int\",\"pro\":\"varchar(20)\"},\"diffCharset\":{\"uat\":\"utf8mb4\",\"pro\":\"utf8mb3\"},\"diffCollate\":{\"uat\":\"utf8mb4_general_ci\",\"pro\":\"utf8mb3_general_ci\"},\"diffDefaultValue\":{\"uat\":\"0\",\"pro\":null},\"diffIfNullable\":{\"uat\":false,\"pro\":true},\"diffComment\":{\"uat\":\"年龄\",\"pro\":\"姓名\"}}", diff);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void test6() {
    String json = "{\n" +
            "  \"columnName\": \"age(source)-name(target)\",\n" +
            "  \"diffName\": {\n" +
            "    \"source\": \"age\",\n" +
            "    \"target\": \"name\"\n" +
            "  },\n" +
            "  \"diffType\": {\n" +
            "    \"source\": \"int\",\n" +
            "    \"target\": \"varchar(20)\"\n" +
            "  },\n" +
            "  \"diffCharset\": {\n" +
            "    \"source\": \"utf8mb4\",\n" +
            "    \"target\": \"utf8mb3\"\n" +
            "  },\n" +
            "  \"diffCollate\": {\n" +
            "    \"source\": \"utf8mb4_general_ci\",\n" +
            "    \"target\": \"utf8mb3_general_ci\"\n" +
            "  },\n" +
            "  \"diffDefaultValue\": {\n" +
            "    \"source\": \"0\",\n" +
            "    \"target\": null\n" +
            "  },\n" +
            "  \"diffIfNullable\": {\n" +
            "    \"source\": false,\n" +
            "    \"target\": true\n" +
            "  },\n" +
            "  \"diffComment\": {\n" +
            "    \"source\": \"年龄\",\n" +
            "    \"target\": \"姓名\"\n" +
            "  }\n" +
            "}";

    DiffTableColumn diffTableColumn = JacksonUtils.toObj(json, new TypeReference<DiffTableColumn>() {
    });

    System.out.println(diffTableColumn);

    Assertions.assertEquals(JacksonUtils.formatJson(json), JacksonUtils.formatJson(diffTableColumn.diff()));
  }

  // ================================================================
  //              TableColumn 序列化 / 反序列化 round-trip
  // ================================================================

  @Test
  void testTableColumnSerializeDeserializeRoundTrip() {
    String json = JacksonUtils.toStr(source);
    TableColumn restored = JacksonUtils.toObj(json, new TypeReference<TableColumn>() {
    });
    Assertions.assertEquals(source, restored);
  }

  // ================================================================
  //              DiffTableColumn 序列化 / 反序列化 round-trip
  // ================================================================

  @Test
  void testDiffTableColumnSerializeDeserializeRoundTrip() {
    DiffTableColumn original = source.absolutelyDiff(targetN);
    String json = original.diff();
    Assertions.assertNotNull(json);
    Assertions.assertNotEquals(Diff.EQUALS, json);

    DiffTableColumn restored = JacksonUtils.toObj(json, new TypeReference<DiffTableColumn>() {
    });
    Assertions.assertEquals(original, restored);
  }

  @Test
  void testDeserializeEqualsRoundTrip() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableColumn.of(null, null, null, null, null, null, null, null, null).diff());
    DiffTableColumn restored = JacksonUtils.toObj(Diff.EQUALS, new TypeReference<DiffTableColumn>() {});
    Assertions.assertTrue(DiffTableColumn.ifNull(restored));
  }

  @Test
  void testDeserializeInvalidStringThrows() {
    MismatchedInputException e = Assertions.assertThrows(MismatchedInputException.class,
            () -> JacksonUtils.toObj("\"foobar\"", new TypeReference<DiffTableColumn>() {}));
    Assertions.assertTrue(e.getMessage().contains("Can not deserialize \"foobar\" to class cn.addenda.ddldiff.bo.diff.DiffTableColumn"));
  }

  // ================================================================
  //              TableColumn 序列化 / 反序列化 null / ""
  // ================================================================

  @Test
  void testTableColumnDeserializeNull() {
    TableColumn result = JacksonUtils.toObj("null", new TypeReference<TableColumn>() {});
    Assertions.assertEquals(TableColumn.of(), result);

    TableColumn result2 = JacksonUtils.toObj("\"\"", new TypeReference<TableColumn>() {});
    Assertions.assertEquals(TableColumn.of(), result2);
  }

}
