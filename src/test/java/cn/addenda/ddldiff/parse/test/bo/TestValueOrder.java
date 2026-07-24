package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.ValueOrder;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffValueOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValueOrder {

  ValueOrder source = ValueOrder.of("asc");

  ValueOrder target1 = ValueOrder.of("desc");

  ValueOrder target2 = ValueOrder.of(" asc ");

  ValueOrder target3 = ValueOrder.of(" DESC ");

  ValueOrder NULL = ValueOrder.of();

  @Test
  void test1() {
    Assertions.assertEquals(true, NULL.runtimeEquals(NULL));
    Assertions.assertEquals(true, source.runtimeEquals(NULL));
    Assertions.assertEquals(false, source.runtimeEquals(target1));
    Assertions.assertEquals(true, source.runtimeEquals(target2));
    Assertions.assertEquals(false, source.runtimeEquals(target3));
    Assertions.assertEquals(true, target1.runtimeEquals(target1));
    Assertions.assertEquals(false, target1.runtimeEquals(target2));
    Assertions.assertEquals(true, target1.runtimeEquals(target3));
    Assertions.assertEquals(false, target2.runtimeEquals(target3));

    Assertions.assertEquals(true, NULL.runtimeEquals(NULL));
    Assertions.assertEquals(true, NULL.runtimeEquals(source));
    Assertions.assertEquals(false, target1.runtimeEquals(source));
    Assertions.assertEquals(true, target2.runtimeEquals(source));
    Assertions.assertEquals(false, target3.runtimeEquals(source));
    Assertions.assertEquals(true, target1.runtimeEquals(target1));
    Assertions.assertEquals(false, target2.runtimeEquals(target1));
    Assertions.assertEquals(true, target3.runtimeEquals(target1));
    Assertions.assertEquals(false, target3.runtimeEquals(target2));
  }

  @Test
  void test2() {
    Assertions.assertEquals(true, NULL.absolutelyEquals(NULL));
    Assertions.assertEquals(false, source.absolutelyEquals(NULL));
    Assertions.assertEquals(false, source.absolutelyEquals(target1));
    Assertions.assertEquals(false, source.absolutelyEquals(target2));
    Assertions.assertEquals(false, source.absolutelyEquals(target3));
    Assertions.assertEquals(true, target1.absolutelyEquals(target1));
    Assertions.assertEquals(false, target1.absolutelyEquals(target2));
    Assertions.assertEquals(false, target1.absolutelyEquals(target3));
    Assertions.assertEquals(false, target2.absolutelyEquals(target3));

    Assertions.assertEquals(true, NULL.absolutelyEquals(NULL));
    Assertions.assertEquals(false, NULL.absolutelyEquals(source));
    Assertions.assertEquals(false, target1.absolutelyEquals(source));
    Assertions.assertEquals(false, target2.absolutelyEquals(source));
    Assertions.assertEquals(false, target3.absolutelyEquals(source));
    Assertions.assertEquals(true, target1.absolutelyEquals(target1));
    Assertions.assertEquals(false, target2.absolutelyEquals(target1));
    Assertions.assertEquals(false, target3.absolutelyEquals(target1));
    Assertions.assertEquals(false, target3.absolutelyEquals(target2));
  }

  @Test
  void test3() {
    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(NULL).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(NULL).diff());
    Assertions.assertEquals(DiffValueOrder.of(source.getValue(), target1.getValue()).diff(), source.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target2).diff());
    Assertions.assertEquals(DiffValueOrder.of(source.getValue(), target3.getValue()).diff(), source.runtimeDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueOrder.of(target1.getValue(), target2.getValue()).diff(), target1.runtimeDiff(target2).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target3).diff());
    Assertions.assertEquals(DiffValueOrder.of(target2.getValue(), target3.getValue()).diff(), target2.runtimeDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(NULL).diff());
    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueOrder.of(target1.getValue(), source.getValue()).diff(), target1.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target2.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueOrder.of(target3.getValue(), source.getValue()).diff(), target3.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueOrder.of(target2.getValue(), target1.getValue()).diff(), target2.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target3.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueOrder.of(target3.getValue(), target2.getValue()).diff(), target3.runtimeDiff(target2).diff());
  }

  @Test
  void test4() {
    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueOrder.of(source.getValue(), NULL.getValue()).diff(), source.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueOrder.of(source.getValue(), target1.getValue()).diff(), source.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueOrder.of(source.getValue(), target2.getValue()).diff(), source.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueOrder.of(source.getValue(), target3.getValue()).diff(), source.absolutelyDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueOrder.of(target1.getValue(), target2.getValue()).diff(), target1.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueOrder.of(target1.getValue(), target3.getValue()).diff(), target1.absolutelyDiff(target3).diff());
    Assertions.assertEquals(DiffValueOrder.of(target2.getValue(), target3.getValue()).diff(), target2.absolutelyDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueOrder.of(NULL.getValue(), source.getValue()).diff(), NULL.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueOrder.of(target1.getValue(), source.getValue()).diff(), target1.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueOrder.of(target2.getValue(), source.getValue()).diff(), target2.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueOrder.of(target3.getValue(), source.getValue()).diff(), target3.absolutelyDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueOrder.of(target2.getValue(), target1.getValue()).diff(), target2.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueOrder.of(target3.getValue(), target1.getValue()).diff(), target3.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueOrder.of(target3.getValue(), target2.getValue()).diff(), target3.absolutelyDiff(target2).diff());
  }

  @Test
  void test5() {
    EnvContext.set("uat", "pro");
    try {
      String diffString = "{\"uat\":\"desc\",\"pro\":\"asc\"}";

      DiffValueOrder diff = target1.absolutelyDiff(source);
//      System.out.println(diff);

      Assertions.assertEquals(diffString, diff.diff());

      DiffValueOrder diffValueOrder = JacksonUtils.toObj(diffString, new TypeReference<DiffValueOrder>() {
      });
      Assertions.assertEquals(diff, diffValueOrder);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeMissingKeys() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueOrder diff1 = JacksonUtils.toObj("{\"uat\":\"asc\"}", new TypeReference<DiffValueOrder>() {});
      Assertions.assertEquals("asc", diff1.getSource());
      Assertions.assertNull(diff1.getTarget());

      DiffValueOrder diff2 = JacksonUtils.toObj("{\"pro\":\"desc\"}", new TypeReference<DiffValueOrder>() {});
      Assertions.assertNull(diff2.getSource());
      Assertions.assertEquals("desc", diff2.getTarget());
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeepClone() {
    Assertions.assertEquals(source, source.deepClone());
    Assertions.assertNotSame(source, source.deepClone());
    Assertions.assertEquals(NULL, NULL.deepClone());
  }

  @Test
  void testDiffWithNull() {
    Assertions.assertEquals("{\"source\":\"asc\",\"target\":null}", source.absolutelyDiff(null).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(null).diff());
  }

  @Test
  void testEqualsAndHashCode() {
    Assertions.assertEquals(source, ValueOrder.of("asc"));
    Assertions.assertNotEquals(source, target1);
    Assertions.assertNotEquals(source, NULL);
    Assertions.assertEquals(source.hashCode(), ValueOrder.of("asc").hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("asc", source.toString());
    Assertions.assertEquals("desc", target1.toString());
  }

  @Test
  void testSerializeNullValue() {
    String json = JacksonUtils.toStr((ValueOrder) null);
    Assertions.assertNull(json);
  }

  @Test
  void testSerializeValueWithNullContent() {
    String json = JacksonUtils.toStr(ValueOrder.of());
    Assertions.assertEquals("null", json);
  }

  @Test
  void testDeserializeNullJson() {
    ValueOrder result = JacksonUtils.toObj("null", new TypeReference<ValueOrder>() {});
    Assertions.assertEquals(ValueOrder.of(), result);
  }

  @Test
  void testDeserializeNullString() {
    ValueOrder result = JacksonUtils.toObj("\"null\"", new TypeReference<ValueOrder>() {});
    Assertions.assertEquals(ValueOrder.of(), result);
  }

  @Test
  void testDeserializeEmptyString() {
    ValueOrder result = JacksonUtils.toObj("\"\"", new TypeReference<ValueOrder>() {});
    Assertions.assertEquals(ValueOrder.of(), result);
  }

  @Test
  void testValueSerializeDeserializeRoundTrip() {
    ValueOrder original = ValueOrder.of("asc");
    String json = JacksonUtils.toStr(original);
    Assertions.assertEquals("\"asc\"", json);
    ValueOrder restored = JacksonUtils.toObj(json, new TypeReference<ValueOrder>() {});
    Assertions.assertEquals(original, restored);
  }

  @Test
  void testDiffSerializeNullSource() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueOrder diff = DiffValueOrder.of(null, "desc");
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":null,\"pro\":\"desc\"}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeNullTarget() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueOrder diff = DiffValueOrder.of("asc", null);
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":\"asc\",\"pro\":null}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeDeserializeRoundTrip() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueOrder original = DiffValueOrder.of("asc", "desc");
      String json = original.diff();
      Assertions.assertEquals("{\"uat\":\"asc\",\"pro\":\"desc\"}", json);
      DiffValueOrder restored = JacksonUtils.toObj(json, new TypeReference<DiffValueOrder>() {});
      Assertions.assertEquals(original, restored);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeJsonNull() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueOrder result = JacksonUtils.toObj("null", new TypeReference<DiffValueOrder>() {});
      Assertions.assertEquals(DiffValueOrder.NULL, result);

      DiffValueOrder result2 = JacksonUtils.toObj("\"\"", new TypeReference<DiffValueOrder>() {});
      Assertions.assertEquals(DiffValueOrder.NULL, result2);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testConsistency() {
    Assertions.assertTrue(source.runtimeEquals(target2));
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target2).diff());
    Assertions.assertTrue(source.absolutelyEquals(source));
    Assertions.assertEquals(Diff.EQUALS, source.absolutelyDiff(source).diff());

    Assertions.assertTrue(source.runtimeEquals(NULL));
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(NULL).diff());
    Assertions.assertFalse(source.absolutelyEquals(NULL));
    Assertions.assertNotEquals(Diff.EQUALS, source.absolutelyDiff(NULL).diff());

    Assertions.assertTrue(NULL.runtimeEquals(NULL));
    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(NULL).diff());
    Assertions.assertTrue(NULL.absolutelyEquals(NULL));
    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());

    Assertions.assertTrue(NULL.runtimeEquals(source));
    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(source).diff());
    Assertions.assertFalse(NULL.absolutelyEquals(source));
    Assertions.assertNotEquals(Diff.EQUALS, NULL.absolutelyDiff(source).diff());

    Assertions.assertTrue(NULL.runtimeEquals(null));
    Assertions.assertFalse(NULL.absolutelyEquals(null));
  }

  @Test
  void testDeserializeEqualsRoundTrip() {
    Assertions.assertEquals(Diff.EQUALS, DiffValueOrder.NULL.diff());
    DiffValueOrder restored = JacksonUtils.toObj(Diff.EQUALS, new TypeReference<DiffValueOrder>() {});
    Assertions.assertTrue(DiffValueOrder.ifNull(restored));
  }

  @Test
  void testDeserializeInvalidStringThrows() {
    MismatchedInputException e = Assertions.assertThrows(MismatchedInputException.class,
            () -> JacksonUtils.toObj("\"foobar\"", new TypeReference<DiffValueOrder>() {}));
    Assertions.assertTrue(e.getMessage().contains("Can not deserialize \"foobar\" to class cn.addenda.ddldiff.bo.diff.DiffValueOrder"));
  }

}
