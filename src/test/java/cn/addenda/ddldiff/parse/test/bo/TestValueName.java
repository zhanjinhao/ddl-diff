package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.ValueName;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffValueName;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValueName {

  ValueName source = ValueName.of("date");

  ValueName target1 = ValueName.of("time");

  ValueName target2 = ValueName.of("`TIME`");

  ValueName target3 = ValueName.of(" TIME ");

  ValueName NULL = ValueName.of();

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
    Assertions.assertEquals(DiffValueName.of(source.getValue(), NULL.getValue()).diff(), source.runtimeDiff(NULL).diff());
    Assertions.assertEquals(DiffValueName.of(source.getValue(), target1.getValue()).diff(), source.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueName.of(source.getValue(), target2.getValue()).diff(), source.runtimeDiff(target2).diff());
    Assertions.assertEquals(DiffValueName.of(source.getValue(), target3.getValue()).diff(), source.runtimeDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target2).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target2.runtimeDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(NULL).diff());
    Assertions.assertEquals(DiffValueName.of(NULL.getValue(), source.getValue()).diff(), NULL.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueName.of(target1.getValue(), source.getValue()).diff(), target1.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueName.of(target2.getValue(), source.getValue()).diff(), target2.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueName.of(target3.getValue(), source.getValue()).diff(), target3.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target2.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target3.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target3.runtimeDiff(target2).diff());
  }

  @Test
  void test4() {
    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueName.of(source.getValue(), NULL.getValue()).diff(), source.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueName.of(source.getValue(), target1.getValue()).diff(), source.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueName.of(source.getValue(), target2.getValue()).diff(), source.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueName.of(source.getValue(), target3.getValue()).diff(), source.absolutelyDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueName.of(target1.getValue(), target2.getValue()).diff(), target1.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueName.of(target1.getValue(), target3.getValue()).diff(), target1.absolutelyDiff(target3).diff());
    Assertions.assertEquals(DiffValueName.of(target2.getValue(), target3.getValue()).diff(), target2.absolutelyDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueName.of(NULL.getValue(), source.getValue()).diff(), NULL.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueName.of(target1.getValue(), source.getValue()).diff(), target1.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueName.of(target2.getValue(), source.getValue()).diff(), target2.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueName.of(target3.getValue(), source.getValue()).diff(), target3.absolutelyDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueName.of(target2.getValue(), target1.getValue()).diff(), target2.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueName.of(target3.getValue(), target1.getValue()).diff(), target3.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueName.of(target3.getValue(), target2.getValue()).diff(), target3.absolutelyDiff(target2).diff());
  }

  @Test
  void test5() {
    EnvContext.set("uat", "pro");
    try {
      String diffString = "{\"uat\":\"time\",\"pro\":\"date\"}";
      DiffValueName diff = target1.absolutelyDiff(source);
//      System.out.println(diff);
      Assertions.assertEquals(diffString, diff.diff());

      DiffValueName diffValueName = JacksonUtils.toObj(diffString, new TypeReference<DiffValueName>() {
      });

      Assertions.assertEquals(diff, diffValueName);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeMissingKeys() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueName diff1 = JacksonUtils.toObj("{\"uat\":\"val\"}", new TypeReference<DiffValueName>() {});
      Assertions.assertEquals("val", diff1.getSource());
      Assertions.assertNull(diff1.getTarget());

      DiffValueName diff2 = JacksonUtils.toObj("{\"pro\":\"val\"}", new TypeReference<DiffValueName>() {});
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
    Assertions.assertEquals(source, ValueName.of("date"));
    Assertions.assertNotEquals(source, target1);
    Assertions.assertNotEquals(source, NULL);
    Assertions.assertEquals(source.hashCode(), ValueName.of("date").hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("date", source.toString());
    Assertions.assertEquals("time", target1.toString());
  }

  @Test
  void testSerializeNullValue() {
    String json = JacksonUtils.toStr((ValueName) null);
    Assertions.assertNull(json);
  }

  @Test
  void testSerializeValueWithNullContent() {
    String json = JacksonUtils.toStr(ValueName.of());
    Assertions.assertEquals("null", json);
  }

  @Test
  void testDeserializeNullJson() {
    ValueName result = JacksonUtils.toObj("null", new TypeReference<ValueName>() {});
    Assertions.assertEquals(ValueName.of(), result);
  }

  @Test
  void testDeserializeNullString() {
    ValueName result = JacksonUtils.toObj("\"null\"", new TypeReference<ValueName>() {});
    Assertions.assertEquals(ValueName.of(), result);
  }

  @Test
  void testDeserializeEmptyString() {
    ValueName result = JacksonUtils.toObj("\"\"", new TypeReference<ValueName>() {});
    Assertions.assertEquals(ValueName.of(), result);
  }

  @Test
  void testValueSerializeDeserializeRoundTrip() {
    ValueName original = ValueName.of("`col_name`");
    String json = JacksonUtils.toStr(original);
    Assertions.assertEquals("\"`col_name`\"", json);
    ValueName restored = JacksonUtils.toObj(json, new TypeReference<ValueName>() {});
    Assertions.assertEquals(original, restored);
  }

  @Test
  void testDiffSerializeNullSource() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueName diff = DiffValueName.of(null, "id");
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":null,\"pro\":\"id\"}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeNullTarget() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueName diff = DiffValueName.of("id", null);
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":\"id\",\"pro\":null}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeDeserializeRoundTrip() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueName original = DiffValueName.of("`col_a`", "`col_b`");
      String json = original.diff();
      Assertions.assertEquals("{\"uat\":\"`col_a`\",\"pro\":\"`col_b`\"}", json);
      DiffValueName restored = JacksonUtils.toObj(json, new TypeReference<DiffValueName>() {});
      Assertions.assertEquals(original, restored);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeJsonNull() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueName result = JacksonUtils.toObj("null", new TypeReference<DiffValueName>() {});
      Assertions.assertEquals(DiffValueName.NULL, result);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testConsistency() {
    Assertions.assertTrue(target1.runtimeEquals(target2));
    Assertions.assertEquals("equals", target1.runtimeDiff(target2).diff());
    Assertions.assertTrue(source.absolutelyEquals(source));
    Assertions.assertEquals("equals", source.absolutelyDiff(source).diff());

    Assertions.assertFalse(source.runtimeEquals(NULL));
    Assertions.assertNotEquals("equals", source.runtimeDiff(NULL).diff());
    Assertions.assertFalse(source.absolutelyEquals(NULL));
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
