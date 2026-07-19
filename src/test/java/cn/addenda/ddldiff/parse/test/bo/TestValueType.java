package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.ValueType;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffValueType;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValueType {

  ValueType source = ValueType.of("date");

  ValueType target1 = ValueType.of("time");

  ValueType target2 = ValueType.of("TIME");

  ValueType target3 = ValueType.of(" TIME ");

  ValueType NULL = ValueType.of();

  @Test
  void test1() {
    Assertions.assertEquals(true, NULL.runtimeEquals(NULL));
    Assertions.assertEquals(false, source.runtimeEquals(NULL));
    Assertions.assertEquals(false, source.runtimeEquals(target1));
    Assertions.assertEquals(false, source.runtimeEquals(target2));
    Assertions.assertEquals(false, source.runtimeEquals(target3));
    Assertions.assertEquals(true, target1.runtimeEquals(target1));
    Assertions.assertEquals(true, target1.runtimeEquals(target2));
    Assertions.assertEquals(true, target1.runtimeEquals(target3));
    Assertions.assertEquals(true, target2.runtimeEquals(target3));

    Assertions.assertEquals(true, NULL.runtimeEquals(NULL));
    Assertions.assertEquals(false, NULL.runtimeEquals(source));
    Assertions.assertEquals(false, target1.runtimeEquals(source));
    Assertions.assertEquals(false, target2.runtimeEquals(source));
    Assertions.assertEquals(false, target3.runtimeEquals(source));
    Assertions.assertEquals(true, target1.runtimeEquals(target1));
    Assertions.assertEquals(true, target2.runtimeEquals(target1));
    Assertions.assertEquals(true, target3.runtimeEquals(target1));
    Assertions.assertEquals(true, target3.runtimeEquals(target2));
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
    Assertions.assertEquals(DiffValueType.of(source.getValue(), NULL.getValue()).diff(), source.runtimeDiff(NULL).diff());
    Assertions.assertEquals(DiffValueType.of(source.getValue(), target1.getValue()).diff(), source.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueType.of(source.getValue(), target2.getValue()).diff(), source.runtimeDiff(target2).diff());
    Assertions.assertEquals(DiffValueType.of(source.getValue(), target3.getValue()).diff(), source.runtimeDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target2).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target2.runtimeDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(NULL).diff());
    Assertions.assertEquals(DiffValueType.of(NULL.getValue(), source.getValue()).diff(), NULL.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueType.of(target1.getValue(), source.getValue()).diff(), target1.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueType.of(target2.getValue(), source.getValue()).diff(), target2.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueType.of(target3.getValue(), source.getValue()).diff(), target3.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target2.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target3.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target3.runtimeDiff(target2).diff());
  }

  @Test
  void test4() {
    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueType.of(source.getValue(), NULL.getValue()).diff(), source.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueType.of(source.getValue(), target1.getValue()).diff(), source.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueType.of(source.getValue(), target2.getValue()).diff(), source.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueType.of(source.getValue(), target3.getValue()).diff(), source.absolutelyDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueType.of(target1.getValue(), target2.getValue()).diff(), target1.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueType.of(target1.getValue(), target3.getValue()).diff(), target1.absolutelyDiff(target3).diff());
    Assertions.assertEquals(DiffValueType.of(target2.getValue(), target3.getValue()).diff(), target2.absolutelyDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueType.of(NULL.getValue(), source.getValue()).diff(), NULL.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueType.of(target1.getValue(), source.getValue()).diff(), target1.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueType.of(target2.getValue(), source.getValue()).diff(), target2.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueType.of(target3.getValue(), source.getValue()).diff(), target3.absolutelyDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueType.of(target2.getValue(), target1.getValue()).diff(), target2.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueType.of(target3.getValue(), target1.getValue()).diff(), target3.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueType.of(target3.getValue(), target2.getValue()).diff(), target3.absolutelyDiff(target2).diff());
  }


  @Test
  void test5() {
    EnvContext.set("uat", "pro");
    try {
      String diffString = "{\"uat\":\"time\",\"pro\":\"date\"}";

      DiffValueType diff = target1.absolutelyDiff(source);
//      System.out.println(diff);
      Assertions.assertEquals(diffString, diff.diff());

      DiffValueType diffValueType = JacksonUtils.toObj(diffString, new TypeReference<DiffValueType>() {
      });

      Assertions.assertEquals(diff, diffValueType);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeMissingKeys() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueType diff1 = JacksonUtils.toObj("{\"uat\":\"val\"}", new TypeReference<DiffValueType>() {
      });
      Assertions.assertEquals("val", diff1.getSource());
      Assertions.assertNull(diff1.getTarget());

      DiffValueType diff2 = JacksonUtils.toObj("{\"pro\":\"val\"}", new TypeReference<DiffValueType>() {
      });
      Assertions.assertNull(diff2.getSource());
      Assertions.assertEquals("val", diff2.getTarget());
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
    Assertions.assertEquals("{\"source\":\"date\",\"target\":null}", source.absolutelyDiff(null).diff());
    Assertions.assertEquals("{\"source\":\"date\",\"target\":null}", source.runtimeDiff(null).diff());
  }

  @Test
  void testEqualsAndHashCode() {
    Assertions.assertEquals(source, ValueType.of("date"));
    Assertions.assertNotEquals(source, target1);
    Assertions.assertNotEquals(source, NULL);
    Assertions.assertEquals(source.hashCode(), ValueType.of("date").hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("date", source.toString());
    Assertions.assertEquals("time", target1.toString());
  }

  @Test
  void testSerializeNullValue() {
    String json = JacksonUtils.toStr((ValueType) null);
    Assertions.assertNull(json);
  }

  @Test
  void testSerializeValueWithNullContent() {
    String json = JacksonUtils.toStr(ValueType.of());
    Assertions.assertEquals("null", json);
  }

  @Test
  void testDeserializeNullJson() {
    ValueType result = JacksonUtils.toObj("null", new TypeReference<ValueType>() {
    });
    Assertions.assertEquals(ValueType.of(), result);
  }

  @Test
  void testDeserializeNullString() {
    ValueType result = JacksonUtils.toObj("\"null\"", new TypeReference<ValueType>() {
    });
    Assertions.assertEquals(ValueType.of(), result);
  }

  @Test
  void testDeserializeEmptyString() {
    ValueType result = JacksonUtils.toObj("\"\"", new TypeReference<ValueType>() {
    });
    Assertions.assertEquals(ValueType.of(), result);
  }

  @Test
  void testValueSerializeDeserializeRoundTrip() {
    ValueType original = ValueType.of("varchar(255)");
    String json = JacksonUtils.toStr(original);
    Assertions.assertEquals("\"varchar(255)\"", json);
    ValueType restored = JacksonUtils.toObj(json, new TypeReference<ValueType>() {
    });
    Assertions.assertEquals(original, restored);
  }

  @Test
  void testDiffSerializeNullSource() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueType diff = DiffValueType.of(null, "int");
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":null,\"pro\":\"int\"}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeNullTarget() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueType diff = DiffValueType.of("int", null);
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":\"int\",\"pro\":null}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeDeserializeRoundTrip() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueType original = DiffValueType.of("varchar", "int");
      String json = original.diff();
      Assertions.assertEquals("{\"uat\":\"varchar\",\"pro\":\"int\"}", json);
      DiffValueType restored = JacksonUtils.toObj(json, new TypeReference<DiffValueType>() {
      });
      Assertions.assertEquals(original, restored);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeJsonNull() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueType result = JacksonUtils.toObj("null", new TypeReference<DiffValueType>() {
      });
      Assertions.assertEquals(DiffValueType.NULL, result);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testConsistency() {
    Assertions.assertTrue(target1.runtimeEquals(target2));
    Assertions.assertEquals("equals", target1.runtimeDiff(target2).diff());
    Assertions.assertFalse(target1.absolutelyEquals(target2));
    Assertions.assertEquals("{\"source\":\"time\",\"target\":\"TIME\"}", target1.absolutelyDiff(target2).diff());
    Assertions.assertTrue(source.absolutelyEquals(source));
    Assertions.assertTrue(source.runtimeEquals(source));
    Assertions.assertEquals("equals", source.absolutelyDiff(source).diff());
    Assertions.assertEquals("equals", source.runtimeDiff(source).diff());

    Assertions.assertFalse(source.runtimeEquals(NULL));
    Assertions.assertFalse(source.absolutelyEquals(NULL));
    Assertions.assertNotEquals("equals", source.runtimeDiff(NULL).diff());
    Assertions.assertNotEquals("equals", source.absolutelyDiff(NULL).diff());

    Assertions.assertTrue(NULL.runtimeEquals(NULL));
    Assertions.assertEquals("equals", NULL.runtimeDiff(NULL).diff());
    Assertions.assertTrue(NULL.absolutelyEquals(NULL));
    Assertions.assertEquals("equals", NULL.absolutelyDiff(NULL).diff());

    Assertions.assertFalse(NULL.runtimeEquals(source));
    Assertions.assertFalse(NULL.absolutelyEquals(source));

    Assertions.assertTrue(NULL.runtimeEquals(null));
    Assertions.assertFalse(NULL.absolutelyEquals(null));
  }

}
