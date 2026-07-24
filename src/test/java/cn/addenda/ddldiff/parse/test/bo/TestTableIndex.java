package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.*;
import cn.addenda.ddldiff.bo.diff.ComparedKey;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTableIndex;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableIndex {

  TableIndexColumns ageName1 = TableIndexColumns.of(
          TableIndexColumn.builder().name(ValueName.of("age")).order(ValueOrder.of()).build(),
          TableIndexColumn.builder().name(ValueName.of("name")).order(ValueOrder.of("desc")).build());

  TableIndexColumns ageName2 = TableIndexColumns.of(
          TableIndexColumn.builder().name(ValueName.of("age")).order(ValueOrder.of("asc")).build(),
          TableIndexColumn.builder().name(ValueName.of("`NAME`")).order(ValueOrder.of(" DESC ")).build());

  TableIndexColumns ageName3 = TableIndexColumns.of(
          TableIndexColumn.builder().name(ValueName.of("age")).order(ValueOrder.of("desc")).build(),
          TableIndexColumn.builder().name(ValueName.of("`NAME`")).order(ValueOrder.of(" asc ")).build());

  TableIndex source = TableIndex.builder()
          .tableIndexColumns(ageName1)
          .name(ValueName.of("ageName"))
          .indexType(IndexType.SIMPLE)
          .comment(ValueComment.of("a"))
          .build();

  TableIndex target1 = TableIndex.builder()
          .tableIndexColumns(ageName2)
          .name(ValueName.of("ageName"))
          .indexType(IndexType.UNIQUE)
          .comment(ValueComment.of("as"))
          .build();

  TableIndex target2 = TableIndex.builder()
          .tableIndexColumns(ageName3)
          .name(ValueName.of("ageName"))
          .indexType(IndexType.UNIQUE)
          .comment(ValueComment.of("asd"))
          .build();

  TableIndex target3 = TableIndex.builder()
          .tableIndexColumns(ageName2)
          .name(ValueName.of("ageName"))
          .indexType(IndexType.UNIQUE)
          .comment(ValueComment.of("asd"))
          .build();

  @Test
  void test1() {
    Assertions.assertEquals(true, source.runtimeEquals(source));
    Assertions.assertEquals(false, source.runtimeEquals(target1));
    Assertions.assertEquals(false, source.runtimeEquals(target2));
    Assertions.assertEquals(false, target1.runtimeEquals(target2));
    Assertions.assertEquals(true, target1.runtimeEquals(target3));
  }

  @Test
  void test2() {
    System.out.println(source.runtimeDiff(source).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(source.getName(), target1.getName()),
                    null, null,
                    DiffTableIndexType.of(source.getIndexType(), target1.getIndexType()), null).diff(),
            source.runtimeDiff(target1).diff());
    System.out.println(source.runtimeDiff(target1).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(source.getName(), target2.getName()),
                    source.getTableIndexColumns().runtimeDiff(target2.getTableIndexColumns()), null,
                    DiffTableIndexType.of(source.getIndexType(), target2.getIndexType()), null).diff(),
            source.runtimeDiff(target2).diff());
    System.out.println(source.runtimeDiff(target2).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(target1.getName(), target2.getName()),
                    target1.getTableIndexColumns().runtimeDiff(target2.getTableIndexColumns()), null,
                    null, null).diff(),
            target1.runtimeDiff(target2).diff());
    System.out.println(target1.runtimeDiff(target2).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(target1.getName(), target3.getName()),
                    target1.getTableIndexColumns().runtimeDiff(target3.getTableIndexColumns()), null,
                    null, null).diff(),
            target1.runtimeDiff(target3).diff());
    System.out.println(target1.runtimeDiff(target3).diff());
  }

  @Test
  void test3() {
    Assertions.assertEquals(true, source.absolutelyEquals(source));
    Assertions.assertEquals(false, source.absolutelyEquals(target1));
    Assertions.assertEquals(false, source.absolutelyEquals(target2));
    Assertions.assertEquals(false, target1.absolutelyEquals(target2));
    Assertions.assertEquals(false, target1.absolutelyEquals(target3));
  }

  @Test
  void test4() {
    System.out.println(source.absolutelyDiff(source).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(source.getName(), target1.getName()),
                    source.getTableIndexColumns().absolutelyDiff(target1.getTableIndexColumns()), source.getName().absolutelyDiff(target1.getName()),
                    DiffTableIndexType.of(source.getIndexType(), target1.getIndexType()), source.getComment().absolutelyDiff(target1.getComment())).diff(),
            source.absolutelyDiff(target1).diff());
    System.out.println(source.absolutelyDiff(target1).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(source.getName(), target2.getName()),
                    source.getTableIndexColumns().absolutelyDiff(target2.getTableIndexColumns()), source.getName().absolutelyDiff(target2.getName()),
                    DiffTableIndexType.of(source.getIndexType(), target2.getIndexType()), source.getComment().absolutelyDiff(target2.getComment())).diff(),
            source.absolutelyDiff(target2).diff());
    System.out.println(source.absolutelyDiff(target2).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(target1.getName(), target2.getName()),
                    target1.getTableIndexColumns().absolutelyDiff(target2.getTableIndexColumns()), target1.getName().absolutelyDiff(target2.getName()),
                    DiffTableIndexType.of(target1.getIndexType(), target2.getIndexType()), target1.getComment().absolutelyDiff(target2.getComment())).diff(),
            target1.absolutelyDiff(target2).diff());
    System.out.println(target1.absolutelyDiff(target2).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(target1.getName(), target3.getName()),
                    target1.getTableIndexColumns().absolutelyDiff(target3.getTableIndexColumns()), target1.getName().absolutelyDiff(target3.getName()),
                    DiffTableIndexType.of(target1.getIndexType(), target2.getIndexType()), target1.getComment().absolutelyDiff(target3.getComment())).diff(),
            target1.absolutelyDiff(target3).diff());
    System.out.println(target1.absolutelyDiff(target3).diff());
  }

  @Test
  void test5() {
    Assertions.assertDoesNotThrow(() -> source.absolutelyDiff(null));
    Assertions.assertDoesNotThrow(() -> source.runtimeDiff(null));

    DiffTableIndex absolutelyDiff = source.absolutelyDiff(null);
    Assertions.assertEquals("{\"indexName\":\"ageName(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age, name desc\",\"target\":null},\"diffName\":{\"source\":\"ageName\",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null},\"diffComment\":{\"source\":\"a\",\"target\":null}}", absolutelyDiff.diff());
    Assertions.assertNotNull(absolutelyDiff);
    Assertions.assertFalse(DiffTableIndex.ifNull(absolutelyDiff));

    DiffTableIndex runtimeDiff = source.runtimeDiff(null);
    Assertions.assertEquals("{\"indexName\":\"ageName(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age, name desc\",\"target\":null},\"diffName\":{\"source\":\"ageName\",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}}", runtimeDiff.diff());
    Assertions.assertNotNull(runtimeDiff);
    Assertions.assertFalse(DiffTableIndex.ifNull(runtimeDiff));
  }

  // ================================================================
  //              补充
  // ================================================================

  @Test
  void testDeepClone() {
    TableIndex cloned = source.deepClone();
    Assertions.assertEquals(source, cloned);
    Assertions.assertNotSame(source, cloned);
    Assertions.assertNotSame(source.getTableIndexColumns(), cloned.getTableIndexColumns());
    Assertions.assertNotSame(source.getName(), cloned.getName());
    Assertions.assertNotSame(source.getComment(), cloned.getComment());
  }

  @Test
  void testEqualsAndHashCode() {
    TableIndex same = TableIndex.builder()
            .tableIndexColumns(ageName1)
            .name(ValueName.of("ageName"))
            .indexType(IndexType.SIMPLE)
            .comment(ValueComment.of("a"))
            .build();
    Assertions.assertEquals(source, same);
    Assertions.assertNotEquals(source, target1);
    Assertions.assertNotEquals(source, TableIndex.of());
    Assertions.assertEquals(source.hashCode(), same.hashCode());
  }

  @Test
  void testToString() {
    System.out.println(JacksonUtils.toStr(source));
    Assertions.assertNotNull(source.toString());
    TableIndex obj = JacksonUtils.toObj("{\"tableIndexColumns\":[\"age\",\"name desc\"],\"name\":\"ageName\",\"indexType\":\"SIMPLE\",\"comment\":\"a\"}", new TypeReference<TableIndex>() {
    });
    Assertions.assertEquals(source, obj);
  }

  @Test
  void testBuilderMissingIndexTypeThrows() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
            TableIndex.builder()
                    .tableIndexColumns(ageName1)
                    .name(ValueName.of("idx"))
                    .comment(ValueComment.of("c"))
                    .build());
  }

  @Test
  void testOf() {
    TableIndex idx = TableIndex.of(ageName1, ValueName.of("idx"), IndexType.UNIQUE, ValueComment.of("c"));
    Assertions.assertEquals(ageName1, idx.getTableIndexColumns());
    Assertions.assertEquals(ValueName.of("idx"), idx.getName());
    Assertions.assertEquals(IndexType.UNIQUE, idx.getIndexType());
    Assertions.assertEquals(ValueComment.of("c"), idx.getComment());
  }

  @Test
  void testOfEmpty() {
    TableIndex idx = TableIndex.of();
    Assertions.assertEquals(TableIndexColumns.of(), idx.getTableIndexColumns());
    Assertions.assertEquals(ValueName.of(), idx.getName());
    Assertions.assertNull(idx.getIndexType());
    Assertions.assertEquals(ValueComment.of(), idx.getComment());
  }

  @Test
  void testRuntimeEqualsWithNull() {
    Assertions.assertFalse(source.runtimeEquals(null));
    Assertions.assertTrue(TableIndex.of().runtimeEquals(null));
  }

  @Test
  void testDiffIfNull() {
    Assertions.assertTrue(DiffTableIndex.ifNull(null));
    Assertions.assertTrue(DiffTableIndex.ifNull(DiffTableIndex.of(
            ComparedKey.of(ValueName.of(), ValueName.of()),
            null, null, null, null)));
  }

  @Test
  void testDiffEqualsAndHashCode() {
    DiffTableIndex d1 = source.absolutelyDiff(target2);
    DiffTableIndex d2 = source.absolutelyDiff(target2);
    Assertions.assertEquals(d1, d2);
    Assertions.assertEquals(d1.hashCode(), d2.hashCode());
  }

  @Test
  void testDiffToString() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndex.of(
            ComparedKey.of(ValueName.of(), ValueName.of()),
            null, null, null, null).toString());
    Assertions.assertEquals(Diff.EQUALS, source.absolutelyDiff(source).toString());
  }

  // ================================================================
  //              testConsistency
  // ================================================================

  TableIndex NULL = TableIndex.of();

  @Test
  void testConsistency() {
    Assertions.assertTrue(source.runtimeEquals(source));
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(source).diff());
    Assertions.assertTrue(source.absolutelyEquals(source));
    Assertions.assertEquals(Diff.EQUALS, source.absolutelyDiff(source).diff());

    Assertions.assertFalse(source.runtimeEquals(NULL));
    Assertions.assertNotEquals(Diff.EQUALS, source.runtimeDiff(NULL).diff());
    Assertions.assertFalse(source.absolutelyEquals(NULL));
    Assertions.assertNotEquals(Diff.EQUALS, source.absolutelyDiff(NULL).diff());

    Assertions.assertTrue(NULL.runtimeEquals(NULL));
    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(NULL).diff());
    Assertions.assertTrue(NULL.absolutelyEquals(NULL));
    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());

    Assertions.assertFalse(NULL.runtimeEquals(source));
    Assertions.assertEquals("{\"indexName\":\"null(source)-ageName(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"age, name desc\"},\"diffName\":{\"source\":null,\"target\":\"ageName\"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}}", NULL.runtimeDiff(source).diff());
    Assertions.assertFalse(NULL.absolutelyEquals(source));
    Assertions.assertEquals("{\"indexName\":\"null(source)-ageName(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"age, name desc\"},\"diffName\":{\"source\":null,\"target\":\"ageName\"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"},\"diffComment\":{\"source\":null,\"target\":\"a\"}}", NULL.absolutelyDiff(source).diff());

    Assertions.assertTrue(NULL.runtimeEquals(null));
    Assertions.assertFalse(NULL.absolutelyEquals(null));
  }

  // ================================================================
  //              TableIndex 序列化 / 反序列化
  // ================================================================

  @Test
  void testSerializeDeserializeRoundTrip() {
    String json = JacksonUtils.toStr(source);
    TableIndex restored = JacksonUtils.toObj(json, new TypeReference<TableIndex>() {
    });
    Assertions.assertEquals(source, restored);
  }

  @Test
  void testSerializeNull() {
    String json = JacksonUtils.toStr((TableIndex) null);
    Assertions.assertNull(json);
  }

  @Test
  void testDeserializeNull() {
    TableIndex result = JacksonUtils.toObj("null", new TypeReference<TableIndex>() {
    });
    Assertions.assertEquals(TableIndex.of(), result);

    TableIndex result2 = JacksonUtils.toObj("\"\"", new TypeReference<TableIndex>() {
    });
    Assertions.assertEquals(TableIndex.of(), result2);
  }

  // ================================================================
  //              DiffTableIndex 序列化 / 反序列化
  // ================================================================

  @Test
  void testDiffSerializeDeserializeRoundTrip() {
    DiffTableIndex original = source.absolutelyDiff(target2);
    String json = original.diff();
    Assertions.assertNotNull(json);
    Assertions.assertNotEquals(Diff.EQUALS, json);

    System.out.println(json);

    DiffTableIndex restored = JacksonUtils.toObj(json, new TypeReference<DiffTableIndex>() {
    });
    Assertions.assertNotNull(restored);
    Assertions.assertFalse(DiffTableIndex.ifNull(restored));
    Assertions.assertEquals(original.getDiffName(), restored.getDiffName());
    Assertions.assertEquals(original.getDiffIndexType(), restored.getDiffIndexType());
    Assertions.assertEquals(original.getDiffComment(), restored.getDiffComment());
  }

  @Test
  void testDiffDeserializeJsonNull() {
    DiffTableIndex result = JacksonUtils.toObj("null", new TypeReference<DiffTableIndex>() {
    });
    Assertions.assertTrue(DiffTableIndex.ifNull(result));

    DiffTableIndex result2 = JacksonUtils.toObj("\"\"", new TypeReference<DiffTableIndex>() {
    });
    Assertions.assertTrue(DiffTableIndex.ifNull(result2));
  }

  @Test
  void testDiffSerializeNull() {
    String json = JacksonUtils.toStr((DiffTableIndex) null);
    Assertions.assertNull(json);
  }

  @Test
  void testDeserializeEqualsRoundTrip() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndex.of(null, null, null, null, null).diff());
    DiffTableIndex restored = JacksonUtils.toObj(Diff.EQUALS, new TypeReference<DiffTableIndex>() {});
    Assertions.assertTrue(DiffTableIndex.ifNull(restored));
  }

  @Test
  void testDeserializeInvalidStringThrows() {
    MismatchedInputException e = Assertions.assertThrows(MismatchedInputException.class,
            () -> JacksonUtils.toObj("\"foobar\"", new TypeReference<DiffTableIndex>() {}));
    Assertions.assertTrue(e.getMessage().contains("Can not deserialize \"foobar\" to class cn.addenda.ddldiff.bo.diff.DiffTableIndex"));
  }

}
