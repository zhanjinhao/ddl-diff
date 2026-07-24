package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.ValueBoolean;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffValueBoolean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValueBoolean {

  ValueBoolean source = ValueBoolean.TRUE;

  ValueBoolean target1 = ValueBoolean.FALSE;

  @Test
  void test1() {
    Assertions.assertEquals(false, source.runtimeEquals(target1));
    Assertions.assertEquals(true, source.runtimeEquals(source));
    Assertions.assertEquals(true, target1.runtimeEquals(target1));

    Assertions.assertEquals(false, target1.runtimeEquals(source));
    Assertions.assertEquals(true, source.runtimeEquals(source));
    Assertions.assertEquals(true, target1.runtimeEquals(target1));
  }

  @Test
  void test2() {
    Assertions.assertEquals(false, source.absolutelyEquals(target1));
    Assertions.assertEquals(true, source.absolutelyEquals(source));
    Assertions.assertEquals(true, target1.absolutelyEquals(target1));

    Assertions.assertEquals(false, target1.absolutelyEquals(source));
    Assertions.assertEquals(true, source.absolutelyEquals(source));
    Assertions.assertEquals(true, target1.absolutelyEquals(target1));
  }

  @Test
  void test3() {
    Assertions.assertEquals(DiffValueBoolean.of(source.isValue(), target1.isValue()).diff(), source.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());

    Assertions.assertEquals(DiffValueBoolean.of(target1.isValue(), source.isValue()).diff(), target1.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
  }

  @Test
  void test4() {
    Assertions.assertEquals(DiffValueBoolean.of(source.isValue(), target1.isValue()).diff(), source.absolutelyDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, source.absolutelyDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());

    Assertions.assertEquals(DiffValueBoolean.of(target1.isValue(), source.isValue()).diff(), target1.absolutelyDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, source.absolutelyDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
  }

  @Test
  void test5() {
    EnvContext.set("uat", "pro");
    try {
      String diffString = "{\"uat\":false,\"pro\":true}";
      DiffValueBoolean diff = target1.absolutelyDiff(source);
//      System.out.println(diff);

      Assertions.assertEquals(diffString, diff.diff());

      DiffValueBoolean diffValueBoolean = JacksonUtils.toObj(diffString, new TypeReference<DiffValueBoolean>() {
      });
      Assertions.assertEquals(diff, diffValueBoolean);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeMissingKeys() {
    EnvContext.set("uat", "pro");
    try {
      IllegalArgumentException ex1 = Assertions.assertThrows(IllegalArgumentException.class,
              () -> JacksonUtils.toObj("{}", new TypeReference<DiffValueBoolean>() {}));
      Assertions.assertTrue(ex1.getMessage().contains("source: null"));
      Assertions.assertTrue(ex1.getMessage().contains("target: null"));

      IllegalArgumentException ex2 = Assertions.assertThrows(IllegalArgumentException.class,
              () -> JacksonUtils.toObj("{\"uat\":true}", new TypeReference<DiffValueBoolean>() {}));
      Assertions.assertTrue(ex2.getMessage().contains("source: true"));
      Assertions.assertTrue(ex2.getMessage().contains("target: null"));

      IllegalArgumentException ex3 = Assertions.assertThrows(IllegalArgumentException.class,
              () -> JacksonUtils.toObj("{\"pro\":false}", new TypeReference<DiffValueBoolean>() {}));
      Assertions.assertTrue(ex3.getMessage().contains("source: null"));
      Assertions.assertTrue(ex3.getMessage().contains("target: false"));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeepClone() {
    Assertions.assertSame(source, source.deepClone());
    Assertions.assertSame(target1, target1.deepClone());
  }

  @Test
  void testDiffWithNull() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> source.absolutelyDiff(null));
    Assertions.assertThrows(IllegalArgumentException.class, () -> source.runtimeDiff(null));
  }

  @Test
  void testEqualsAndHashCode() {
    Assertions.assertEquals(source, source);
    Assertions.assertNotEquals(source, target1);
    Assertions.assertEquals(source.hashCode(), source.hashCode());
    Assertions.assertNotEquals(source.hashCode(), target1.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("true", source.toString());
    Assertions.assertEquals("false", target1.toString());
  }

  @Test
  void testSerializeNullValue() {
    String json = JacksonUtils.toStr((ValueBoolean) null);
    Assertions.assertNull(json);
  }

  @Test
  void testSerializeValue() {
    Assertions.assertEquals("true", JacksonUtils.toStr(ValueBoolean.TRUE));
    Assertions.assertEquals("false", JacksonUtils.toStr(ValueBoolean.FALSE));
  }

  @Test
  void testDeserializeNullJsonThrows() {
    Assertions.assertThrows(IllegalArgumentException.class,
            () -> JacksonUtils.toObj("null", new TypeReference<ValueBoolean>() {}));
  }

  @Test
  void testDeserializeNullStringThrows() {
    Assertions.assertThrows(IllegalArgumentException.class,
            () -> JacksonUtils.toObj("\"null\"", new TypeReference<ValueBoolean>() {}));
  }

  @Test
  void testDeserializeEmptyStringThrows() {
    Assertions.assertThrows(IllegalArgumentException.class,
            () -> JacksonUtils.toObj("\"\"", new TypeReference<ValueBoolean>() {}));
  }

  @Test
  void testDeserializeCaseInsensitive() {
    Assertions.assertEquals(ValueBoolean.TRUE, JacksonUtils.toObj("\"True\"", new TypeReference<ValueBoolean>() {}));
    Assertions.assertEquals(ValueBoolean.TRUE, JacksonUtils.toObj("\"TRUE\"", new TypeReference<ValueBoolean>() {}));
    Assertions.assertEquals(ValueBoolean.FALSE, JacksonUtils.toObj("\"False\"", new TypeReference<ValueBoolean>() {}));
    Assertions.assertEquals(ValueBoolean.FALSE, JacksonUtils.toObj("\"FALSE\"", new TypeReference<ValueBoolean>() {}));
  }

  @Test
  void testValueSerializeDeserializeRoundTrip() {
    String json = JacksonUtils.toStr(ValueBoolean.TRUE);
    Assertions.assertEquals("true", json);
    ValueBoolean restored = JacksonUtils.toObj(json, new TypeReference<ValueBoolean>() {});
    Assertions.assertEquals(ValueBoolean.TRUE, restored);

    String json2 = JacksonUtils.toStr(ValueBoolean.FALSE);
    Assertions.assertEquals("false", json2);
    ValueBoolean restored2 = JacksonUtils.toObj(json2, new TypeReference<ValueBoolean>() {});
    Assertions.assertEquals(ValueBoolean.FALSE, restored2);
  }

  @Test
  void testDiffDeserializeJsonNull() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueBoolean result = JacksonUtils.toObj("null", new TypeReference<DiffValueBoolean>() {});
      Assertions.assertEquals(DiffValueBoolean.NULL, result);

      DiffValueBoolean result2 = JacksonUtils.toObj("\"\"", new TypeReference<DiffValueBoolean>() {});
      Assertions.assertEquals(DiffValueBoolean.NULL, result2);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testConsistency() {
    Assertions.assertTrue(source.runtimeEquals(source));
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(source).diff());
    Assertions.assertTrue(source.absolutelyEquals(source));
    Assertions.assertEquals(Diff.EQUALS, source.absolutelyDiff(source).diff());

    Assertions.assertFalse(source.runtimeEquals(null));
    Assertions.assertThrows(IllegalArgumentException.class, () -> source.runtimeDiff(null));
    Assertions.assertFalse(source.absolutelyEquals(null));
    Assertions.assertThrows(IllegalArgumentException.class, () -> source.absolutelyDiff(null));

    Assertions.assertFalse(target1.runtimeEquals(null));
    Assertions.assertThrows(IllegalArgumentException.class, () -> target1.runtimeDiff(null));
    Assertions.assertFalse(target1.absolutelyEquals(null));
    Assertions.assertThrows(IllegalArgumentException.class, () -> target1.absolutelyDiff(null));
  }

  @Test
  void testDeserializeEqualsRoundTrip() {
    Assertions.assertEquals(Diff.EQUALS, DiffValueBoolean.NULL.diff());
    DiffValueBoolean restored = JacksonUtils.toObj(Diff.EQUALS, new TypeReference<DiffValueBoolean>() {});
    Assertions.assertTrue(DiffValueBoolean.ifNull(restored));
  }

  @Test
  void testDeserializeInvalidStringThrows() {
    MismatchedInputException e = Assertions.assertThrows(MismatchedInputException.class,
            () -> JacksonUtils.toObj("\"foobar\"", new TypeReference<DiffValueBoolean>() {}));
    Assertions.assertTrue(e.getMessage().contains("Can not deserialize \"foobar\" to class cn.addenda.ddldiff.bo.diff.DiffValueBoolean"));
  }

}
