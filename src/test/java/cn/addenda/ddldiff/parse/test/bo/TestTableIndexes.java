package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.*;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class TestTableIndexes {

  TableIndex source = TableIndex.builder()
          .tableIndexColumns(TableIndexColumns.of(
                  TableIndexColumn.builder().name(ValueName.of("age")).order(ValueOrder.of()).build()))
          .name(ValueName.of("idx_age"))
          .indexType(IndexType.SIMPLE)
          .comment(ValueComment.of())
          .build();

  TableIndex target1 = TableIndex.builder()
          .tableIndexColumns(TableIndexColumns.of(
                  TableIndexColumn.builder().name(ValueName.of("age")).order(ValueOrder.of("asc")).build()))
          .name(ValueName.of(" IDX_AGE "))
          .indexType(IndexType.SIMPLE)
          .comment(ValueComment.of())
          .build();

  TableIndex target2 = TableIndex.builder()
          .tableIndexColumns(TableIndexColumns.of(
                  TableIndexColumn.builder().name(ValueName.of("name")).order(ValueOrder.of("desc")).build()))
          .name(ValueName.of("idx_name"))
          .indexType(IndexType.UNIQUE)
          .comment(ValueComment.of())
          .build();

  TableIndex target3 = TableIndex.builder()
          .tableIndexColumns(TableIndexColumns.of(
                  TableIndexColumn.builder().name(ValueName.of("grade")).order(ValueOrder.of()).build()))
          .name(ValueName.of("idx_grade"))
          .indexType(IndexType.SIMPLE)
          .comment(ValueComment.of())
          .build();

  TableIndex extra = TableIndex.builder()
          .tableIndexColumns(TableIndexColumns.of(
                  TableIndexColumn.builder().name(ValueName.of("col1")).order(ValueOrder.of("desc")).build()))
          .name(ValueName.of("idx_unique_extra"))
          .indexType(IndexType.UNIQUE)
          .comment(ValueComment.of("extra comment"))
          .build();

  @Test
  void testRuntimeEqualsSameIndexesDifferentOrder() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target1, target2);
    TableIndexes tableIndexes2 = TableIndexes.of(target2, target1, source);

    Assertions.assertEquals(true, tableIndexes1.runtimeEquals(tableIndexes2));
  }

  @Test
  void testRuntimeEqualsBothEmpty() {
    TableIndexes tableIndexes1 = TableIndexes.of();
    TableIndexes tableIndexes2 = TableIndexes.of();

    Assertions.assertEquals(true, tableIndexes1.runtimeEquals(tableIndexes2));
  }

  @Test
  void testRuntimeEqualsOneEmpty() {
    TableIndexes tableIndexes1 = TableIndexes.of(source);
    TableIndexes tableIndexes2 = TableIndexes.of();
    Assertions.assertEquals("[{\"indexName\":\"idx_age(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age\",\"target\":null},\"diffName\":{\"source\":\"idx_age\",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}}]", tableIndexes1.runtimeDiff(tableIndexes2).diff());
    Assertions.assertEquals("[{\"indexName\":\"null(source)-idx_age(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"age\"},\"diffName\":{\"source\":null,\"target\":\"idx_age\"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}}]", tableIndexes2.runtimeDiff(tableIndexes1).diff());
    Assertions.assertEquals(false, tableIndexes1.runtimeEquals(tableIndexes2));
    Assertions.assertEquals(false, tableIndexes2.runtimeEquals(tableIndexes1));
  }

  @Test
  void testRuntimeEqualsDifferentSets() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target2);
    TableIndexes tableIndexes2 = TableIndexes.of(target2, target3);
    Assertions.assertEquals("[{\"indexName\":\"idx_age(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age\",\"target\":null},\"diffName\":{\"source\":\"idx_age\",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}},{\"indexName\":\"null(source)-idx_grade(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"grade\"},\"diffName\":{\"source\":null,\"target\":\"idx_grade\"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}}]", tableIndexes1.runtimeDiff(tableIndexes2).diff());
    Assertions.assertEquals(false, tableIndexes1.runtimeEquals(tableIndexes2));
  }

  @Test
  void testRuntimeEqualsSelfVsVariant() {
    TableIndexes tableIndexes1 = TableIndexes.of(source);
    TableIndexes tableIndexes2 = TableIndexes.of(target1);

    Assertions.assertEquals(true, tableIndexes1.runtimeEquals(tableIndexes2));
  }

  @Test
  void testRuntimeDiffMatching() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target1, target2);
    TableIndexes tableIndexes2 = TableIndexes.of(target2, target1, source);

    Assertions.assertEquals(Diff.EQUALS, tableIndexes1.runtimeDiff(tableIndexes2).diff());
  }

  @Test
  void testRuntimeDiffSourceExtra() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target1, target2, extra);
    TableIndexes tableIndexes2 = TableIndexes.of(target2, target1);

    DiffTableIndexes diff = tableIndexes1.runtimeDiff(tableIndexes2);
    Assertions.assertEquals("[{\"indexName\":\" IDX_AGE (source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age asc\",\"target\":null},\"diffName\":{\"source\":\" IDX_AGE \",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}},{\"indexName\":\"idx_unique_extra(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"col1 desc\",\"target\":null},\"diffName\":{\"source\":\"idx_unique_extra\",\"target\":null},\"diffIndexType\":{\"source\":\"UNIQUE\",\"target\":null}}]", diff.diff());
    Assertions.assertFalse(DiffTableIndexes.ifNull(diff));
    Assertions.assertNotNull(diff.diff());
    Assertions.assertNotEquals(Diff.EQUALS, diff.diff());
  }

  @Test
  void testRuntimeDiffTargetExtra() {
    TableIndexes tableIndexes1 = TableIndexes.of(target2, target1);
    TableIndexes tableIndexes2 = TableIndexes.of(source, target1, target2, extra);

    DiffTableIndexes diff = tableIndexes1.runtimeDiff(tableIndexes2);
    Assertions.assertEquals("[{\"indexName\":\"null(source)- IDX_AGE (target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"age asc\"},\"diffName\":{\"source\":null,\"target\":\" IDX_AGE \"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}},{\"indexName\":\"null(source)-idx_unique_extra(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"col1 desc\"},\"diffName\":{\"source\":null,\"target\":\"idx_unique_extra\"},\"diffIndexType\":{\"source\":null,\"target\":\"UNIQUE\"}}]", diff.diff());
    Assertions.assertFalse(DiffTableIndexes.ifNull(diff));
    Assertions.assertNotNull(diff.diff());
    Assertions.assertNotEquals(Diff.EQUALS, diff.diff());
  }

  @Test
  void testRuntimeDiffBothEmpty() {
    TableIndexes tableIndexes1 = TableIndexes.of();
    TableIndexes tableIndexes2 = TableIndexes.of();

    Assertions.assertEquals(Diff.EQUALS, tableIndexes1.runtimeDiff(tableIndexes2).diff());
  }

  @Test
  void testRuntimeDiffOnlySource() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target2);
    TableIndexes tableIndexes2 = TableIndexes.of();

    DiffTableIndexes diff = tableIndexes1.runtimeDiff(tableIndexes2);
    Assertions.assertEquals("[{\"indexName\":\"idx_age(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age\",\"target\":null},\"diffName\":{\"source\":\"idx_age\",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}},{\"indexName\":\"idx_name(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"name desc\",\"target\":null},\"diffName\":{\"source\":\"idx_name\",\"target\":null},\"diffIndexType\":{\"source\":\"UNIQUE\",\"target\":null}}]", diff.diff());
    DiffTableIndexes expected = DiffTableIndexes.of(ArrayUtils.asArrayList(
            source.runtimeDiff(TableIndex.of()),
            target2.runtimeDiff(TableIndex.of())));
    Assertions.assertEquals(expected.diff(), diff.diff());
    System.out.println(diff.diff());
    DiffTableIndexes parsed = JacksonUtils.toObj("[\n" +
            "  {\n" +
            "    \"indexName\": \"idx_age(source)-null(target)\",\n" +
            "    \"diffTableIndexColumns\": {\n" +
            "      \"source\": \"age\",\n" +
            "      \"target\": null\n" +
            "    },\n" +
            "    \"diffName\": {\n" +
            "      \"source\": \"idx_age\",\n" +
            "      \"target\": null\n" +
            "    },\n" +
            "    \"diffIndexType\": {\n" +
            "      \"source\": \"SIMPLE\",\n" +
            "      \"target\": null\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"indexName\": \"idx_name(source)-null(target)\",\n" +
            "    \"diffTableIndexColumns\": {\n" +
            "      \"source\": \"name desc\",\n" +
            "      \"target\": null\n" +
            "    },\n" +
            "    \"diffName\": {\n" +
            "      \"source\": \"idx_name\",\n" +
            "      \"target\": null\n" +
            "    },\n" +
            "    \"diffIndexType\": {\n" +
            "      \"source\": \"UNIQUE\",\n" +
            "      \"target\": null\n" +
            "    }\n" +
            "  }\n" +
            "]", new TypeReference<DiffTableIndexes>() {
    });

    Assertions.assertEquals(diff, parsed);
  }

  @Test
  void testRuntimeDiffOnlyTarget() {
    TableIndexes tableIndexes1 = TableIndexes.of();
    TableIndexes tableIndexes2 = TableIndexes.of(source, target2);

    DiffTableIndexes diff = tableIndexes1.runtimeDiff(tableIndexes2);
    Assertions.assertEquals("[{\"indexName\":\"null(source)-idx_age(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"age\"},\"diffName\":{\"source\":null,\"target\":\"idx_age\"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}},{\"indexName\":\"null(source)-idx_name(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"name desc\"},\"diffName\":{\"source\":null,\"target\":\"idx_name\"},\"diffIndexType\":{\"source\":null,\"target\":\"UNIQUE\"}}]", diff.diff());
    DiffTableIndexes expected = DiffTableIndexes.of(ArrayUtils.asArrayList(
            TableIndex.of().runtimeDiff(source),
            TableIndex.of().runtimeDiff(target2)));
    Assertions.assertEquals(expected.diff(), diff.diff());

    DiffTableIndexes parsed = JacksonUtils.toObj("[{\"indexName\":\"null(source)-idx_age(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"age\"},\"diffName\":{\"source\":null,\"target\":\"idx_age\"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}},{\"indexName\":\"null(source)-idx_name(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"name desc\"},\"diffName\":{\"source\":null,\"target\":\"idx_name\"},\"diffIndexType\":{\"source\":null,\"target\":\"UNIQUE\"}}]", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertEquals(diff, parsed);
  }

  @Test
  void testRuntimeDiffBothSidesExtra() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target1, target2);
    TableIndexes tableIndexes2 = TableIndexes.of(target2, target3);

    DiffTableIndexes diff = tableIndexes1.runtimeDiff(tableIndexes2);
    System.out.println(diff.diff());
    Assertions.assertEquals("[{\"indexName\":\"idx_age(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age\",\"target\":null},\"diffName\":{\"source\":\"idx_age\",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}},{\"indexName\":\" IDX_AGE (source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age asc\",\"target\":null},\"diffName\":{\"source\":\" IDX_AGE \",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}},{\"indexName\":\"null(source)-idx_grade(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"grade\"},\"diffName\":{\"source\":null,\"target\":\"idx_grade\"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}}]", diff.diff());
    Assertions.assertFalse(DiffTableIndexes.ifNull(diff));
    Assertions.assertFalse(Diff.EQUALS.equals(diff.diff()));

    DiffTableIndexes parsed = JacksonUtils.toObj("[{\"indexName\":\"idx_age(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age\",\"target\":null},\"diffName\":{\"source\":\"idx_age\",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}},{\"indexName\":\" IDX_AGE (source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age asc\",\"target\":null},\"diffName\":{\"source\":\" IDX_AGE \",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}},{\"indexName\":\"null(source)-idx_grade(target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"grade\"},\"diffName\":{\"source\":null,\"target\":\"idx_grade\"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}}]", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertEquals(diff, parsed);
  }

  @Test
  void testRuntimeDiffRemoveByNameNotFound() {
    TableIndexes indexes = TableIndexes.of(source, target2);
    Assertions.assertNull(indexes.removeTableIndexRuntime(ValueName.of("nonexistent")));
    Assertions.assertEquals(2, indexes.indexSize());
  }

  @Test
  void testAbsolutelyEqualsSameOrder() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target1, target2);
    TableIndexes tableIndexes2 = TableIndexes.of(source, target1, target2);

    Assertions.assertEquals(true, tableIndexes1.absolutelyEquals(tableIndexes2));
  }

  @Test
  void testAbsolutelyEqualsDifferentOrder() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target1, target2);
    TableIndexes tableIndexes2 = TableIndexes.of(target2, target1, source);
    Assertions.assertEquals("[{\"indexName\":\"idx_age(source)-idx_name(target)\",\"diffTableIndexColumns\":{\"source\":\"age\",\"target\":\"name desc\"},\"diffName\":{\"source\":\"idx_age\",\"target\":\"idx_name\"},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":\"UNIQUE\"}},{\"indexName\":\"idx_name(source)-idx_age(target)\",\"diffTableIndexColumns\":{\"source\":\"name desc\",\"target\":\"age\"},\"diffName\":{\"source\":\"idx_name\",\"target\":\"idx_age\"},\"diffIndexType\":{\"source\":\"UNIQUE\",\"target\":\"SIMPLE\"}}]", tableIndexes1.absolutelyDiff(tableIndexes2).diff());
    Assertions.assertEquals(false, tableIndexes1.absolutelyEquals(tableIndexes2));
    Assertions.assertFalse(DiffTableIndexes.ifNull(tableIndexes1.absolutelyDiff(tableIndexes2)));
    DiffTableIndexes parsed = JacksonUtils.toObj("[{\"indexName\":\"idx_age(source)-idx_name(target)\",\"diffTableIndexColumns\":{\"source\":\"age\",\"target\":\"name desc\"},\"diffName\":{\"source\":\"idx_age\",\"target\":\"idx_name\"},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":\"UNIQUE\"}},{\"indexName\":\"idx_name(source)-idx_age(target)\",\"diffTableIndexColumns\":{\"source\":\"name desc\",\"target\":\"age\"},\"diffName\":{\"source\":\"idx_name\",\"target\":\"idx_age\"},\"diffIndexType\":{\"source\":\"UNIQUE\",\"target\":\"SIMPLE\"}}]", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertEquals(parsed, tableIndexes1.absolutelyDiff(tableIndexes2));
  }

  @Test
  void testAbsolutelyEqualsDifferentLength() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target1);
    TableIndexes tableIndexes2 = TableIndexes.of(source);
    Assertions.assertEquals("[{\"indexName\":\" IDX_AGE (source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age asc\",\"target\":null},\"diffName\":{\"source\":\" IDX_AGE \",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}}]", tableIndexes1.absolutelyDiff(tableIndexes2).diff());
    Assertions.assertEquals(false, tableIndexes1.absolutelyEquals(tableIndexes2));
    Assertions.assertFalse(DiffTableIndexes.ifNull(tableIndexes1.absolutelyDiff(tableIndexes2)));
    DiffTableIndexes parsed = JacksonUtils.toObj("[{\"indexName\":\" IDX_AGE (source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"age asc\",\"target\":null},\"diffName\":{\"source\":\" IDX_AGE \",\"target\":null},\"diffIndexType\":{\"source\":\"SIMPLE\",\"target\":null}}]", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertEquals(parsed, tableIndexes1.absolutelyDiff(tableIndexes2));
  }

  @Test
  void testAbsolutelyDiffSameOrder() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target1, target2);
    TableIndexes tableIndexes2 = TableIndexes.of(source, target1, target2);

    DiffTableIndexes diff = tableIndexes1.absolutelyDiff(tableIndexes2);
    Assertions.assertEquals(Diff.EQUALS, diff.diff());
    Assertions.assertTrue(DiffTableIndexes.ifNull(diff));
  }

  @Test
  void testAbsolutelyDiffSourceExtra() {
    TableIndexes tableIndexes1 = TableIndexes.of(source, target1, target2);
    TableIndexes tableIndexes2 = TableIndexes.of(source, target1);

    DiffTableIndexes diff = tableIndexes1.absolutelyDiff(tableIndexes2);
    Assertions.assertEquals("[{\"indexName\":\"idx_name(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"name desc\",\"target\":null},\"diffName\":{\"source\":\"idx_name\",\"target\":null},\"diffIndexType\":{\"source\":\"UNIQUE\",\"target\":null}}]", diff.diff());
    Assertions.assertFalse(DiffTableIndexes.ifNull(diff));
    DiffTableIndexes parsed = JacksonUtils.toObj("[{\"indexName\":\"idx_name(source)-null(target)\",\"diffTableIndexColumns\":{\"source\":\"name desc\",\"target\":null},\"diffName\":{\"source\":\"idx_name\",\"target\":null},\"diffIndexType\":{\"source\":\"UNIQUE\",\"target\":null}}]", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertEquals(parsed, diff);
  }

  @Test
  void testAbsolutelyDiffTargetExtra() {
    TableIndexes tableIndexes1 = TableIndexes.of(source);
    TableIndexes tableIndexes2 = TableIndexes.of(source, target1);

    DiffTableIndexes diff = tableIndexes1.absolutelyDiff(tableIndexes2);
    Assertions.assertEquals("[{\"indexName\":\"null(source)- IDX_AGE (target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"age asc\"},\"diffName\":{\"source\":null,\"target\":\" IDX_AGE \"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}}]", diff.diff());
    Assertions.assertFalse(DiffTableIndexes.ifNull(diff));
    DiffTableIndexes parsed = JacksonUtils.toObj("[{\"indexName\":\"null(source)- IDX_AGE (target)\",\"diffTableIndexColumns\":{\"source\":null,\"target\":\"age asc\"},\"diffName\":{\"source\":null,\"target\":\" IDX_AGE \"},\"diffIndexType\":{\"source\":null,\"target\":\"SIMPLE\"}}]", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertEquals(parsed, diff);
  }

  @Test
  void testAddTableIndexNullThrows() {
    TableIndexes indexes = TableIndexes.of();
    Assertions.assertThrows(UnsupportedOperationException.class, () -> indexes.addTableIndex(null));
  }

  @Test
  void testDeepClone() {
    TableIndexes original = TableIndexes.of(source, target1, target2);
    TableIndexes cloned = original.deepClone();
    Assertions.assertEquals(original, cloned);
    Assertions.assertNotSame(original, cloned);

    java.util.Iterator<TableIndex> origIt = original.iterator();
    java.util.Iterator<TableIndex> cloneIt = cloned.iterator();
    while (origIt.hasNext()) {
      TableIndex orig = origIt.next();
      TableIndex clone = cloneIt.next();
      Assertions.assertEquals(orig, clone);
      Assertions.assertNotSame(orig, clone);
    }
  }

  // ================================================================
  //              testConsistency
  // ================================================================

  TableIndexes NULL = TableIndexes.of();

  @Test
  void testConsistency() {
    TableIndexes list = TableIndexes.of(source, target2);

    Assertions.assertTrue(list.runtimeEquals(list));
    Assertions.assertEquals(Diff.EQUALS, list.runtimeDiff(list).diff());
    Assertions.assertTrue(list.absolutelyEquals(list));
    Assertions.assertEquals(Diff.EQUALS, list.absolutelyDiff(list).diff());

    Assertions.assertFalse(list.runtimeEquals(NULL));
    Assertions.assertNotEquals(Diff.EQUALS, list.runtimeDiff(NULL).diff());
    Assertions.assertFalse(list.absolutelyEquals(NULL));
    Assertions.assertNotEquals(Diff.EQUALS, list.absolutelyDiff(NULL).diff());

    Assertions.assertTrue(NULL.runtimeEquals(NULL));
    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(NULL).diff());
    Assertions.assertTrue(NULL.absolutelyEquals(NULL));
    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());

    Assertions.assertFalse(NULL.runtimeEquals(list));
    Assertions.assertFalse(NULL.absolutelyEquals(list));

    Assertions.assertTrue(NULL.runtimeEquals(null));
    Assertions.assertFalse(NULL.absolutelyEquals(null));
  }

  // ================================================================
  //              equals / hashCode / indexSize / iterator / of
  // ================================================================

  @Test
  void testEqualsAndHashCode() {
    TableIndexes list1 = TableIndexes.of(source, target1, target2);
    TableIndexes list2 = TableIndexes.of(source, target1, target2);
    TableIndexes list3 = TableIndexes.of(target2, target1, source);

    Assertions.assertEquals(list1, list2);
    Assertions.assertEquals(list1.hashCode(), list2.hashCode());
    Assertions.assertNotEquals(list1, list3);
    Assertions.assertNotEquals(list1.hashCode(), list3.hashCode());
    Assertions.assertNotEquals(list1, TableIndexes.of());
    Assertions.assertNotEquals(list1, null);
  }

  @Test
  void testIndexSize() {
    Assertions.assertEquals(0, TableIndexes.of().indexSize());
    Assertions.assertEquals(3, TableIndexes.of(source, target1, target2).indexSize());
  }

  @Test
  void testIterator() {
    TableIndexes empty = TableIndexes.of();
    Assertions.assertFalse(empty.iterator().hasNext());

    TableIndexes list = TableIndexes.of(source, target2);
    Iterator<TableIndex> it = list.iterator();
    Assertions.assertTrue(it.hasNext());
    Assertions.assertEquals(source, it.next());
    Assertions.assertTrue(it.hasNext());
    Assertions.assertEquals(target2, it.next());
    Assertions.assertFalse(it.hasNext());
  }

  @Test
  void testOfEmpty() {
    Assertions.assertEquals(0, TableIndexes.of().indexSize());
  }

  @Test
  void testOfNullVarargs() {
    Assertions.assertEquals(0, TableIndexes.of((TableIndex[]) null).indexSize());
  }

  @Test
  void testOfNormal() {
    TableIndexes list = TableIndexes.of(source, target1, target2);
    Assertions.assertEquals(3, list.indexSize());
    Assertions.assertEquals(source, list.iterator().next());
  }

  // ================================================================
  //              removeTableIndex
  // ================================================================

  @Test
  void testRemoveTableIndexAbsolutely() {
    TableIndexes indexes = TableIndexes.of(source, target1, target2);
    TableIndex removed = indexes.removeTableIndexAbsolutely(ValueName.of("idx_age"));
    Assertions.assertEquals(source, removed);
    Assertions.assertEquals(2, indexes.indexSize());

    Assertions.assertNull(indexes.removeTableIndexAbsolutely(ValueName.of("nonexistent")));
    Assertions.assertEquals(2, indexes.indexSize());
  }

  @Test
  void testRemoveTableIndexRuntime() {
    TableIndexes indexes = TableIndexes.of(source, target1, target2);
    TableIndex removed = indexes.removeTableIndexRuntime(ValueName.of(" IDX_AGE "));
    Assertions.assertEquals(source, removed);
    Assertions.assertEquals(2, indexes.indexSize());

    Assertions.assertNull(indexes.removeTableIndexRuntime(ValueName.of("nonexistent")));
    Assertions.assertEquals(2, indexes.indexSize());
  }

  // ================================================================
  //              DiffTableIndexes 基本方法
  // ================================================================

  @Test
  void testDiffTableIndexesIfNull() {
    Assertions.assertTrue(DiffTableIndexes.ifNull(null));
    Assertions.assertTrue(DiffTableIndexes.ifNull(DiffTableIndexes.NULL));
    Assertions.assertTrue(DiffTableIndexes.ifNull(DiffTableIndexes.of(null)));
    Assertions.assertTrue(DiffTableIndexes.ifNull(DiffTableIndexes.of(new java.util.ArrayList<>())));

    DiffTableIndexes nonNull = TableIndexes.of(source).runtimeDiff(TableIndexes.of(target2));
    Assertions.assertFalse(DiffTableIndexes.ifNull(nonNull));
  }

  @Test
  void testDiffTableIndexesEqualsAndHashCode() {
    TableIndexes list1 = TableIndexes.of(source);
    TableIndexes list2 = TableIndexes.of(target2);
    DiffTableIndexes d1 = list1.absolutelyDiff(list2);
    DiffTableIndexes d2 = list1.absolutelyDiff(list2);
    Assertions.assertEquals(d1, d2);
    Assertions.assertEquals(d1.hashCode(), d2.hashCode());
  }

  @Test
  void testDiffTableIndexesToString() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexes.NULL.toString());
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexes.of(null).toString());

    DiffTableIndexes nonNull = TableIndexes.of(source).runtimeDiff(TableIndexes.of(target2));
    System.out.println(nonNull.diff());
    Assertions.assertTrue(nonNull.toString().contains("indexName"));
  }

  // ================================================================
  //              TableIndexes 序列化 / 反序列化
  // ================================================================

  @Test
  void testTableIndexesSerializeDeserializeRoundTrip() {
    TableIndexes original = TableIndexes.of(source, target1, target2);
    String json = JacksonUtils.toStr(original);
    Assertions.assertNotNull(json);

    TableIndexes restored = JacksonUtils.toObj(json, new TypeReference<TableIndexes>() {
    });
    Assertions.assertEquals(original, restored);
    Assertions.assertEquals(3, restored.indexSize());
  }

  @Test
  void testTableIndexesSerializeNull() {
    String json = JacksonUtils.toStr((TableIndexes) null);
    Assertions.assertNull(json);
  }

  @Test
  void testTableIndexesDeserializeJsonNull() {
    TableIndexes result = JacksonUtils.toObj("null", new TypeReference<TableIndexes>() {
    });
    Assertions.assertEquals(TableIndexes.of(), result);

    TableIndexes result2 = JacksonUtils.toObj("\"\"", new TypeReference<TableIndexes>() {
    });
    Assertions.assertEquals(TableIndexes.of(), result2);
  }

  @Test
  void testTableIndexesDeserializeEmptyArray() {
    TableIndexes result = JacksonUtils.toObj("[]", new TypeReference<TableIndexes>() {
    });
    Assertions.assertEquals(TableIndexes.of(), result);
  }

  // ================================================================
  //              DiffTableIndexes 序列化 / 反序列化
  // ================================================================

  @Test
  void testDiffTableIndexesSerializeDeserializeRoundTrip() {
    TableIndexes list1 = TableIndexes.of(source, target2);
    TableIndexes list2 = TableIndexes.of(target3);
    DiffTableIndexes original = list1.absolutelyDiff(list2);
    String json = original.diff();
    Assertions.assertNotNull(json);
    Assertions.assertNotEquals(Diff.EQUALS, json);

    DiffTableIndexes restored = JacksonUtils.toObj(json, new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertNotNull(restored);
    Assertions.assertFalse(DiffTableIndexes.ifNull(restored));
    Assertions.assertEquals(original, restored);
  }

  @Test
  void testDiffTableIndexesSerializeNull() {
    String json = JacksonUtils.toStr((DiffTableIndexes) null);
    Assertions.assertNull(json);
  }

  @Test
  void testDiffTableIndexesDeserializeJsonNull() {
    DiffTableIndexes result = JacksonUtils.toObj("null", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertTrue(DiffTableIndexes.ifNull(result));

    DiffTableIndexes result2 = JacksonUtils.toObj("\"\"", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertTrue(DiffTableIndexes.ifNull(result2));
  }

  @Test
  void testDiffTableIndexesDeserializeEquals() {
    DiffTableIndexes result = JacksonUtils.toObj("\"\"", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertTrue(DiffTableIndexes.ifNull(result));
  }

  @Test
  void testDiffTableIndexesDeserializeEmptyArray() {
    DiffTableIndexes result = JacksonUtils.toObj("[]", new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertTrue(DiffTableIndexes.ifNull(result));
  }

  // ================================================================
  //              diffWithNull
  // ================================================================

  @Test
  void testDiffWithNull() {
    TableIndexes list = TableIndexes.of(source, target2);
    Assertions.assertDoesNotThrow(() -> list.absolutelyDiff(null));
    Assertions.assertDoesNotThrow(() -> list.runtimeDiff(null));
    Assertions.assertDoesNotThrow(() -> list.runtimeEquals(null));

    DiffTableIndexes absolutelyDiff = list.absolutelyDiff(null);
    Assertions.assertFalse(DiffTableIndexes.ifNull(absolutelyDiff));
    Assertions.assertNotEquals(Diff.EQUALS, absolutelyDiff.diff());

    DiffTableIndexes runtimeDiff = list.runtimeDiff(null);
    Assertions.assertFalse(DiffTableIndexes.ifNull(runtimeDiff));
    Assertions.assertNotEquals(Diff.EQUALS, runtimeDiff.diff());

    DiffTableIndexes emptyNullDiff = TableIndexes.of().absolutelyDiff(null);
    Assertions.assertEquals(Diff.EQUALS, emptyNullDiff.diff());

    DiffTableIndexes emptyNullRuntime = TableIndexes.of().runtimeDiff(null);
    Assertions.assertEquals(Diff.EQUALS, emptyNullRuntime.diff());
  }

  @Test
  void testDeserializeEqualsRoundTrip() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexes.NULL.diff());
    DiffTableIndexes restored = JacksonUtils.toObj(Diff.EQUALS, new TypeReference<DiffTableIndexes>() {
    });
    Assertions.assertTrue(DiffTableIndexes.ifNull(restored));
  }

  @Test
  void testDeserializeInvalidStringThrows() {
    MismatchedInputException e = Assertions.assertThrows(MismatchedInputException.class,
            () -> JacksonUtils.toObj("\"foobar\"", new TypeReference<DiffTableIndexes>() {
            }));
    Assertions.assertTrue(e.getMessage().contains("Can not deserialize \"foobar\" to class cn.addenda.ddldiff.bo.diff.DiffTableIndexes"));
  }

}
