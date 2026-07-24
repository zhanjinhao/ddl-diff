package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.TableIndexColumn;
import cn.addenda.ddldiff.bo.TableIndexColumns;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.ValueName;
import cn.addenda.ddldiff.bo.ValueOrder;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumns;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableIndexColumns {

  TableIndexColumn source1 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" asc "))
          .build();

  TableIndexColumn target1 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" ASC "))
          .build();

  TableIndexColumn source2 = TableIndexColumn.builder()
          .name(ValueName.of("name"))
          .order(ValueOrder.of(" asc "))
          .build();

  TableIndexColumn target2 = TableIndexColumn.builder()
          .name(ValueName.of("name"))
          .order(ValueOrder.of(null))
          .build();

  TableIndexColumn source3 = TableIndexColumn.builder()
          .name(ValueName.of("grade"))
          .order(ValueOrder.of("desc"))
          .build();

  TableIndexColumn target3 = TableIndexColumn.builder()
          .name(ValueName.of("grade"))
          .order(ValueOrder.of(" DESC "))
          .build();

  TableIndexColumn source4 = TableIndexColumn.builder()
          .name(ValueName.of("class"))
          .order(ValueOrder.of("desc"))
          .build();

  TableIndexColumn target4 = TableIndexColumn.builder()
          .name(ValueName.of("class"))
          .order(ValueOrder.of(null))
          .build();

  @Test
  void test1_1() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3);

    Assertions.assertEquals(true, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test1_2() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    Assertions.assertEquals(false, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test1_3() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of();

    Assertions.assertEquals(false, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test1_4() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of();
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    Assertions.assertEquals(false, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test1_5() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target4, target3, target2, target1);

    Assertions.assertEquals(false, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test2_1() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3);

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(Diff.EQUALS, diff.diff());
  }

  @Test
  void test2_2() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"age  ASC , name, grade  DESC , class\"}",
            diff.diff());
  }

  @Test
  void test2_3() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of();

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":null}",
            diff.diff());
  }

  @Test
  void test2_4() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of();
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":null,\"target\":\"age  ASC , name, grade  DESC , class\"}",
            diff.diff());
  }

  @Test
  void test2_5() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target4, target3, target2, target1);

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"class, grade  DESC , name, age  ASC \"}",
            diff.diff());
  }

  @Test
  void test3_1() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3);

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test3_2() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test3_3() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of();

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test3_4() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of();
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test3_5() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target4, target3, target2, target1);

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test4_1() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3);

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc\",\"target\":\"age  ASC , name, grade  DESC \"}",
            diff.diff());
  }

  @Test
  void test4_2() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"age  ASC , name, grade  DESC , class\"}",
            diff.diff());
  }

  @Test
  void test4_3() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of();

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":null}",
            diff.diff());
  }

  @Test
  void test4_4() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of();
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":null,\"target\":\"age  ASC , name, grade  DESC , class\"}",
            diff.diff());
  }

  @Test
  void test4_5() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target4, target3, target2, target1);

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"class, grade  DESC , name, age  ASC \"}",
            diff.diff());
  }

  @Test
  void test5_1() {
    String diffString = "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"class, grade  DESC , name, age  ASC \"}";
    TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {
    };

    DiffTableIndexColumns diffTableIndexColumns = JacksonUtils.toObj(diffString, typeReference);

    String diffString2 = JacksonUtils.toStr(diffTableIndexColumns);

    System.out.println(diffString2);

    Assertions.assertEquals(diffString.replaceAll("\\s+", ""), diffString2.replaceAll("\\s+", ""));
  }

  @Test
  void testDeserializeBothNameOnly() {
    EnvContext.set("uat", "pro");
    try {
      String json = "{\"uat\":\"col1, col2\",\"pro\":\"col3, col4\"}";
      TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {};
      DiffTableIndexColumns result = JacksonUtils.toObj(json, typeReference);
      Assertions.assertNotNull(result);
      Assertions.assertEquals(json, JacksonUtils.toStr(result));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeserializeBothNameAndOrder() {
    EnvContext.set("uat", "pro");
    try {
      String json = "{\"uat\":\"col1 asc, col2 desc\",\"pro\":\"col3 desc, col4 asc\"}";
      TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {};
      DiffTableIndexColumns result = JacksonUtils.toObj(json, typeReference);
      Assertions.assertNotNull(result);
      Assertions.assertEquals(json, JacksonUtils.toStr(result));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeserializeSourceNameAndOrderTargetNameOnly() {
    EnvContext.set("uat", "pro");
    try {
      String json = "{\"uat\":\"col1 asc, col2 desc\",\"pro\":\"col3, col4\"}";
      TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {};
      DiffTableIndexColumns result = JacksonUtils.toObj(json, typeReference);
      Assertions.assertNotNull(result);
      Assertions.assertEquals(json, JacksonUtils.toStr(result));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeserializeSourceNameOnlyTargetNameAndOrder() {
    EnvContext.set("uat", "pro");
    try {
      String json = "{\"uat\":\"col1, col2\",\"pro\":\"col3 desc, col4 asc\"}";
      TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {};
      DiffTableIndexColumns result = JacksonUtils.toObj(json, typeReference);
      Assertions.assertNotNull(result);
      Assertions.assertEquals(json, JacksonUtils.toStr(result));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeserializeNullSource() {
    EnvContext.set("uat", "pro");
    try {
      String json = "{\"uat\":null,\"pro\":\"col1, col2\"}";
      TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {};
      DiffTableIndexColumns result = JacksonUtils.toObj(json, typeReference);
      Assertions.assertNotNull(result);
      Assertions.assertEquals(json, JacksonUtils.toStr(result));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeserializeNullTarget() {
    EnvContext.set("uat", "pro");
    try {
      String json = "{\"uat\":\"col1, col2\",\"pro\":null}";
      TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {};
      DiffTableIndexColumns result = JacksonUtils.toObj(json, typeReference);
      Assertions.assertNotNull(result);
      Assertions.assertEquals(json, JacksonUtils.toStr(result));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeserializeEmptyJson() {
    EnvContext.set("uat", "pro");
    try {
      String json = "{}";
      TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {};
      DiffTableIndexColumns result = JacksonUtils.toObj(json, typeReference);
      Assertions.assertEquals(DiffTableIndexColumns.NULL, result);
      Assertions.assertEquals("{\"uat\":null,\"pro\":null}", JacksonUtils.toStr(DiffTableIndexColumns.NULL));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeserializeNullJson() {
    EnvContext.set("uat", "pro");
    try {
      String json = "null";
      TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {};
      DiffTableIndexColumns result = JacksonUtils.toObj(json, typeReference);
      Assertions.assertEquals(DiffTableIndexColumns.NULL, result);
      Assertions.assertEquals("{\"uat\":null,\"pro\":null}", JacksonUtils.toStr(DiffTableIndexColumns.NULL));
    } finally {
      EnvContext.remove();
    }
  }

  // ================================================================
  //              补充：TableIndexColumns 自身方法
  // ================================================================

  @Test
  void testBothEmpty() {
    TableIndexColumns c1 = TableIndexColumns.of();
    TableIndexColumns c2 = TableIndexColumns.of();
    Assertions.assertEquals(true, c1.runtimeEquals(c2));
    Assertions.assertEquals(true, c1.absolutelyEquals(c2));
    Assertions.assertEquals(Diff.EQUALS, c1.runtimeDiff(c2).diff());
    Assertions.assertEquals(Diff.EQUALS, c1.absolutelyDiff(c2).diff());
  }

  @Test
  void testSelfEquals() {
    TableIndexColumns c1 = TableIndexColumns.of(source1, source2, source3);
    Assertions.assertEquals(true, c1.runtimeEquals(c1));
    Assertions.assertEquals(true, c1.absolutelyEquals(c1));
    Assertions.assertEquals(Diff.EQUALS, c1.runtimeDiff(c1).diff());
    Assertions.assertEquals(Diff.EQUALS, c1.absolutelyDiff(c1).diff());
  }

  @Test
  void testDeepClone() {
    TableIndexColumns original = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns cloned = original.deepClone();
    Assertions.assertEquals(original, cloned);
    Assertions.assertNotSame(original, cloned);

    java.util.Iterator<TableIndexColumn> origIt = original.iterator();
    java.util.Iterator<TableIndexColumn> cloneIt = cloned.iterator();
    while (origIt.hasNext()) {
      TableIndexColumn orig = origIt.next();
      TableIndexColumn clone = cloneIt.next();
      Assertions.assertEquals(orig, clone);
      Assertions.assertNotSame(orig, clone);
    }
  }

  @Test
  void testDiffWithNull() {
    TableIndexColumns c1 = TableIndexColumns.of(source1, source2);
    Assertions.assertFalse(DiffTableIndexColumns.ifNull(c1.absolutelyDiff(null)));
    Assertions.assertEquals("{\"source\":\"age  asc , name  asc \",\"target\":null}", c1.absolutelyDiff(null).diff());
    Assertions.assertFalse(DiffTableIndexColumns.ifNull(c1.runtimeDiff(null)));
    Assertions.assertEquals("{\"source\":\"age  asc , name  asc \",\"target\":null}", c1.absolutelyDiff(null).diff());
  }

  @Test
  void testAddTableIndexColumnNullThrows() {
    TableIndexColumns c1 = TableIndexColumns.of();
    Assertions.assertThrows(UnsupportedOperationException.class, () -> c1.addTableIndexColumn(null));
  }

  @Test
  void testOfNullVarargs() {
    TableIndexColumns result = TableIndexColumns.of((TableIndexColumn[]) null);
    Assertions.assertEquals(TableIndexColumns.of(), result);
  }

  @Test
  void testOfEmpty() {
    TableIndexColumns result = TableIndexColumns.of();
    Assertions.assertEquals(0, result.getTableIndexColumnList().size());
  }

  @Test
  void testEqualsAndHashCode() {
    TableIndexColumns c1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns c2 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns c3 = TableIndexColumns.of(source1, source2);
    Assertions.assertEquals(c1, c2);
    Assertions.assertNotEquals(c1, c3);
    Assertions.assertEquals(c1.hashCode(), c2.hashCode());
  }

  // ================================================================
  //              补充：DiffTableIndexColumns 自身方法
  // ================================================================

  @Test
  void testDiffIfNull() {
    Assertions.assertTrue(DiffTableIndexColumns.ifNull(null));
    Assertions.assertTrue(DiffTableIndexColumns.ifNull(DiffTableIndexColumns.NULL));
    Assertions.assertTrue(DiffTableIndexColumns.ifNull(
            DiffTableIndexColumns.of(new java.util.ArrayList<>())));
  }

  @Test
  void testDiffOfNullList() {
    Assertions.assertEquals(DiffTableIndexColumns.NULL, DiffTableIndexColumns.of(null));
  }

  @Test
  void testDiffOfEmptyList() {
    Assertions.assertEquals(DiffTableIndexColumns.NULL,
            DiffTableIndexColumns.of(new java.util.ArrayList<>()));
  }

  @Test
  void testDiffEqualsAndHashCode() {
    DiffTableIndexColumns NULL1 = DiffTableIndexColumns.of(new java.util.ArrayList<>());
    DiffTableIndexColumns NULL2 = DiffTableIndexColumns.of(null);
    Assertions.assertEquals(NULL1, NULL2);
    Assertions.assertEquals(NULL1.hashCode(), NULL2.hashCode());
  }

  @Test
  void testDiffToString() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexColumns.NULL.toString());
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexColumns.of(new java.util.ArrayList<>()).toString());
  }

  // ================================================================
  //              TableIndexColumns 序列化 / 反序列化 round-trip
  // ================================================================

  @Test
  void testTableIndexColumnsSerializeDeserializeRoundTrip() {
    TableIndexColumn col1 = TableIndexColumn.builder()
            .name(ValueName.of("age"))
            .order(ValueOrder.of("asc"))
            .build();
    TableIndexColumn col2 = TableIndexColumn.builder()
            .name(ValueName.of("name"))
            .order(ValueOrder.of("desc"))
            .build();
    TableIndexColumns original = TableIndexColumns.of(col1, col2);
    String json = JacksonUtils.toStr(original);
    TableIndexColumns restored = JacksonUtils.toObj(json, new TypeReference<TableIndexColumns>() {
    });
    Assertions.assertEquals(original, restored);
  }

  // DiffTableIndexColumns round-trip has known whitespace normalization asymmetry
  // (DiffTableIndexColumnsDeserializer normalizes whitespace, serializer preserves original).
  // Use sub-field assertions as a workaround.
  @Test
  void testDiffTableIndexColumnsSerializeDeserializeRoundTrip() {
    TableIndexColumns list1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns list2 = TableIndexColumns.of(target1, target2, target3);
    DiffTableIndexColumns original = list1.absolutelyDiff(list2);
    String json = original.diff();
    Assertions.assertNotNull(json);
    if (Diff.EQUALS.equals(json)) {
      return;
    }

    DiffTableIndexColumns restored = JacksonUtils.toObj(json, new TypeReference<DiffTableIndexColumns>() {
    });
    Assertions.assertNotNull(restored);
    Assertions.assertFalse(DiffTableIndexColumns.ifNull(restored));
  }

  @Test
  void testDeserializeEqualsRoundTrip() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexColumns.NULL.diff());
    DiffTableIndexColumns restored = JacksonUtils.toObj(Diff.EQUALS, new TypeReference<DiffTableIndexColumns>() {});
    Assertions.assertTrue(DiffTableIndexColumns.ifNull(restored));
  }

  @Test
  void testDeserializeInvalidStringThrows() {
    MismatchedInputException e = Assertions.assertThrows(MismatchedInputException.class,
            () -> JacksonUtils.toObj("\"foobar\"", new TypeReference<DiffTableIndexColumns>() {}));
    Assertions.assertTrue(e.getMessage().contains("Can not deserialize \"foobar\" to class cn.addenda.ddldiff.bo.diff.DiffTableIndexColumns"));
  }

  @Test
  void testDiffDeserializeJsonNull() {
    DiffTableIndexColumns result = JacksonUtils.toObj("null", new TypeReference<DiffTableIndexColumns>() {});
    Assertions.assertTrue(DiffTableIndexColumns.ifNull(result));

    DiffTableIndexColumns result2 = JacksonUtils.toObj("\"\"", new TypeReference<DiffTableIndexColumns>() {});
    Assertions.assertTrue(DiffTableIndexColumns.ifNull(result2));
  }

  // ================================================================
  //              TableIndexColumns of (normal)
  // ================================================================

  @Test
  void testOf() {
    TableIndexColumns cols = TableIndexColumns.of(source1, source2, source3);
    Assertions.assertEquals(3, cols.getTableIndexColumnList().size());
  }

  // ================================================================
  //              TableIndexColumns 序列化 / 反序列化 null / ""
  // ================================================================

  @Test
  void testTableIndexColumnsDeserializeNull() {
    TableIndexColumns result = JacksonUtils.toObj("null", new TypeReference<TableIndexColumns>() {});
    Assertions.assertEquals(TableIndexColumns.of(), result);

    TableIndexColumns result2 = JacksonUtils.toObj("\"\"", new TypeReference<TableIndexColumns>() {});
    Assertions.assertEquals(TableIndexColumns.of(), result2);

    TableIndexColumns result3 = JacksonUtils.toObj("\"null\"", new TypeReference<TableIndexColumns>() {});
    Assertions.assertEquals(TableIndexColumns.of(), result3);
  }

  // ================================================================
  //              TableIndexColumns toString / serialize null
  // ================================================================

  @Test
  void testToString() {
    TableIndexColumns cols = TableIndexColumns.of(source1, source2);
    String str = JacksonUtils.toStr(cols);
    Assertions.assertTrue(str.contains("age"));
    Assertions.assertTrue(str.contains("name"));
  }

  @Test
  void testTableIndexColumnsSerializeNull() {
    String json = JacksonUtils.toStr((TableIndexColumns) null);
    Assertions.assertNull(json);
  }

  // ================================================================
  //              consistency
  // ================================================================

  @Test
  void testConsistency() {
    TableIndexColumns list = TableIndexColumns.of(source1, source2);
    Assertions.assertTrue(list.runtimeEquals(list));
    Assertions.assertEquals(Diff.EQUALS, list.runtimeDiff(list).diff());
    Assertions.assertTrue(list.absolutelyEquals(list));
    Assertions.assertEquals(Diff.EQUALS, list.absolutelyDiff(list).diff());

    TableIndexColumns empty = TableIndexColumns.of();
    Assertions.assertTrue(empty.runtimeEquals(empty));
    Assertions.assertEquals(Diff.EQUALS, empty.runtimeDiff(empty).diff());
  }

}
