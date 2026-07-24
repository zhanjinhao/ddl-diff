package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.TableIndexColumn;
import cn.addenda.ddldiff.bo.ValueName;
import cn.addenda.ddldiff.bo.ValueOrder;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumn;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableIndexColumn {

  TableIndexColumn source = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" asc "))
          .build();

  TableIndexColumn target1 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" ASC "))
          .build();

  TableIndexColumn target2 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of("desc"))
          .build();

  TableIndexColumn target3 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" DESC "))
          .build();

  TableIndexColumn targetN = TableIndexColumn.builder()
          .name(ValueName.of("name"))
          .order(ValueOrder.of(" DESC "))
          .build();

  TableIndexColumn NULL = TableIndexColumn.of();

  // ================================================================
  //              runtimeEquals
  // ================================================================

  @Test
  void testRuntimeEquals() {
    Assertions.assertEquals(true, source.runtimeEquals(source));
    Assertions.assertEquals(true, source.runtimeEquals(target1));
    Assertions.assertEquals(false, source.runtimeEquals(target2));
    Assertions.assertEquals(false, source.runtimeEquals(target3));
    Assertions.assertEquals(false, source.runtimeEquals(targetN));
    Assertions.assertEquals(false, source.runtimeEquals(NULL));
    Assertions.assertEquals(true, NULL.runtimeEquals(NULL));

    Assertions.assertEquals(false, target2.runtimeEquals(source));
    Assertions.assertEquals(false, targetN.runtimeEquals(source));
    Assertions.assertEquals(true, target1.runtimeEquals(source));
    Assertions.assertEquals(true, target2.runtimeEquals(target3));
  }

  // ================================================================
  //              absolutelyEquals
  // ================================================================

  @Test
  void testAbsolutelyEquals() {
    Assertions.assertEquals(true, source.absolutelyEquals(source));
    Assertions.assertEquals(false, source.absolutelyEquals(target1));
    Assertions.assertEquals(false, source.absolutelyEquals(target2));
    Assertions.assertEquals(false, source.absolutelyEquals(target3));
    Assertions.assertEquals(false, source.absolutelyEquals(targetN));
    Assertions.assertEquals(false, source.absolutelyEquals(NULL));
    Assertions.assertEquals(true, NULL.absolutelyEquals(NULL));

    Assertions.assertEquals(false, target1.absolutelyEquals(source));
    Assertions.assertEquals(false, target2.absolutelyEquals(source));
  }

  // ================================================================
  //              runtimeDiff
  // ================================================================

  @Test
  void testRuntimeDiff() {
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target1).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().runtimeDiff(target2.getOrder())).diff(),
            source.runtimeDiff(target2).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().runtimeDiff(target3.getOrder())).diff(),
            source.runtimeDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target2.runtimeDiff(target3).diff());

    Assertions.assertEquals(
            DiffTableIndexColumn.of(source.getName().runtimeDiff(targetN.getName()), source.getOrder().runtimeDiff(targetN.getOrder())).diff(),
            source.runtimeDiff(targetN).diff());
  }

  // ================================================================
  //              absolutelyDiff
  // ================================================================

  @Test
  void testAbsolutelyDiff() {
    Assertions.assertEquals(Diff.EQUALS, source.absolutelyDiff(source).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().absolutelyDiff(target1.getOrder())).diff(),
            source.absolutelyDiff(target1).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().absolutelyDiff(target2.getOrder())).diff(),
            source.absolutelyDiff(target2).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().absolutelyDiff(target3.getOrder())).diff(),
            source.absolutelyDiff(target3).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(source.getName().absolutelyDiff(targetN.getName()), source.getOrder().absolutelyDiff(targetN.getOrder())).diff(),
            source.absolutelyDiff(targetN).diff());
  }

  // ================================================================
  //              序列化 / 反序列化
  // ================================================================

  @Test
  void testDiffJsonRoundTrip() {
    EnvContext.set("uat", "pro");
    try {
      String expected = "{\"diffName\":{\"uat\":\"age\",\"pro\":\"name\"},\"diffOrder\":{\"uat\":\" asc \",\"pro\":\" DESC \"}}";
      DiffTableIndexColumn diff = source.absolutelyDiff(targetN);
      Assertions.assertEquals(expected, diff.diff());

      DiffTableIndexColumn restored = JacksonUtils.toObj(expected, new TypeReference<DiffTableIndexColumn>() {
      });
      Assertions.assertEquals(diff, restored);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeJsonNull() {
    EnvContext.set("uat", "pro");
    try {
      DiffTableIndexColumn result = JacksonUtils.toObj("null", new TypeReference<DiffTableIndexColumn>() {
      });
      Assertions.assertTrue(DiffTableIndexColumn.ifNull(result));

      DiffTableIndexColumn result2 = JacksonUtils.toObj("\"\"", new TypeReference<DiffTableIndexColumn>() {
      });
      Assertions.assertTrue(DiffTableIndexColumn.ifNull(result2));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeMissingKeys() {
    EnvContext.set("uat", "pro");
    try {
      DiffTableIndexColumn diff1 = JacksonUtils.toObj("{}", new TypeReference<DiffTableIndexColumn>() {
      });
      Assertions.assertTrue(DiffTableIndexColumn.ifNull(diff1));
    } finally {
      EnvContext.remove();
    }
  }

  // ================================================================
  //              deepClone / diffWithNull / equals / toString
  // ================================================================

  @Test
  void testDeepClone() {
    TableIndexColumn cloned = source.deepClone();
    Assertions.assertEquals(source, cloned);
    Assertions.assertNotSame(source, cloned);
    Assertions.assertEquals(NULL, NULL.deepClone());
  }

  @Test
  void testDiffWithNull() {
    Assertions.assertNotEquals(Diff.EQUALS, source.absolutelyDiff(null).diff());
    Assertions.assertEquals("{\"diffName\":{\"source\":\"age\",\"target\":null},\"diffOrder\":{\"source\":\" asc \",\"target\":null}}", source.absolutelyDiff(null).diff());
    Assertions.assertNotEquals(Diff.EQUALS, source.runtimeDiff(null).diff());
    Assertions.assertEquals("{\"diffName\":{\"source\":\"age\",\"target\":null}}", source.runtimeDiff(null).diff());
  }

  @Test
  void testEqualsAndHashCode() {
    TableIndexColumn same = TableIndexColumn.builder()
            .name(ValueName.of("age"))
            .order(ValueOrder.of(" asc "))
            .build();
    Assertions.assertEquals(source, same);
    Assertions.assertNotEquals(source, targetN);
    Assertions.assertNotEquals(source, NULL);
    Assertions.assertEquals(source.hashCode(), same.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotNull(source.toString());
  }

  // ================================================================
  //              Value 序列化 / 反序列化
  // ================================================================

  @Test
  void testSerializeNullValue() {
    String json = JacksonUtils.toStr((TableIndexColumn) null);
    Assertions.assertNull(json);
  }

  @Test
  void testSerializeValueWithNullContent() {
    String json = JacksonUtils.toStr(TableIndexColumn.of());
    Assertions.assertEquals("\"\"", json);
  }

  @Test
  void testSerializeNameOnly() {
    TableIndexColumn col = TableIndexColumn.builder().name(ValueName.of("age")).build();
    String json = JacksonUtils.toStr(col);
    Assertions.assertEquals("\"age\"", json);
  }

  @Test
  void testSerializeNameAndOrder() {
    String json = JacksonUtils.toStr(target2);
    Assertions.assertEquals("\"age desc\"", json);
  }

  @Test
  void testDeserializeNullJson() {
    TableIndexColumn result = JacksonUtils.toObj("null", new TypeReference<TableIndexColumn>() {
    });
    Assertions.assertEquals(TableIndexColumn.of(), result);
  }

  @Test
  void testDeserializeEmptyString() {
    TableIndexColumn result = JacksonUtils.toObj("\"\"", new TypeReference<TableIndexColumn>() {
    });
    Assertions.assertEquals(TableIndexColumn.of(), result);
  }

  @Test
  void testDeserializeNullString() {
    TableIndexColumn result = JacksonUtils.toObj("\"null\"", new TypeReference<TableIndexColumn>() {
    });
    Assertions.assertEquals(TableIndexColumn.of(), result);
  }

  @Test
  void testDeserializeNameOnly() {
    TableIndexColumn result = JacksonUtils.toObj("\"age\"", new TypeReference<TableIndexColumn>() {
    });
    Assertions.assertEquals(ValueName.of("age"), result.getName());
    Assertions.assertEquals(ValueOrder.of(), result.getOrder());
  }

  @Test
  void testDeserializeNameAndOrder() {
    TableIndexColumn result = JacksonUtils.toObj("\"age desc\"", new TypeReference<TableIndexColumn>() {
    });
    Assertions.assertEquals(ValueName.of("age"), result.getName());
    Assertions.assertEquals(ValueOrder.of("desc"), result.getOrder());
  }

  @Test
  void testDeserializeMultipleSpaces() {
    TableIndexColumn result = JacksonUtils.toObj("\"age   desc\"", new TypeReference<TableIndexColumn>() {
    });
    Assertions.assertEquals(ValueName.of("age"), result.getName());
    Assertions.assertEquals(ValueOrder.of("desc"), result.getOrder());
    Assertions.assertEquals("\"age desc\"", JacksonUtils.toStr(result));
  }

  @Test
  void testValueSerializeDeserializeRoundTrip() {
    TableIndexColumn original = TableIndexColumn.builder()
            .name(ValueName.of("col1"))
            .order(ValueOrder.of("asc"))
            .build();
    String json = JacksonUtils.toStr(original);
    TableIndexColumn restored = JacksonUtils.toObj(json, new TypeReference<TableIndexColumn>() {
    });
    Assertions.assertEquals(original, restored);
    Assertions.assertEquals(json, JacksonUtils.toStr(restored));
  }

  @Test
  void testConsistency() {
    Assertions.assertTrue(source.runtimeEquals(target1));
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target1).diff());
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
    Assertions.assertFalse(NULL.absolutelyEquals(source));

    Assertions.assertTrue(NULL.runtimeEquals(null));
    Assertions.assertFalse(NULL.absolutelyEquals(null));
  }

  @Test
  void testDeserializeEqualsRoundTrip() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexColumn.of(null, null).diff());
    DiffTableIndexColumn restored = JacksonUtils.toObj(Diff.EQUALS, new TypeReference<DiffTableIndexColumn>() {});
    Assertions.assertTrue(DiffTableIndexColumn.ifNull(restored));
  }

  @Test
  void testDeserializeInvalidStringThrows() {
    MismatchedInputException e = Assertions.assertThrows(MismatchedInputException.class,
            () -> JacksonUtils.toObj("\"foobar\"", new TypeReference<DiffTableIndexColumn>() {}));
    Assertions.assertTrue(e.getMessage().contains("Can not deserialize \"foobar\" to class cn.addenda.ddldiff.bo.diff.DiffTableIndexColumn"));
  }

}
