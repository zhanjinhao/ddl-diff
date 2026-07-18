package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.ValueString;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffValueString;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValueString {

  ValueString source = ValueString.of("date");

  ValueString target1 = ValueString.of("time");

  ValueString target2 = ValueString.of("TIME");

  ValueString target3 = ValueString.of(" TIME ");

  ValueString NULL = ValueString.of();

  @Test
  void test1() {
    Assertions.assertEquals(true, NULL.runtimeEquals(NULL));
    Assertions.assertEquals(false, source.runtimeEquals(NULL));
    Assertions.assertEquals(false, source.runtimeEquals(target1));
    Assertions.assertEquals(false, source.runtimeEquals(target2));
    Assertions.assertEquals(false, source.runtimeEquals(target3));
    Assertions.assertEquals(true, target1.runtimeEquals(target1));
    Assertions.assertEquals(false, target1.runtimeEquals(target2));
    Assertions.assertEquals(false, target1.runtimeEquals(target3));
    Assertions.assertEquals(false, target2.runtimeEquals(target3));

    Assertions.assertEquals(true, NULL.runtimeEquals(NULL));
    Assertions.assertEquals(false, NULL.runtimeEquals(source));
    Assertions.assertEquals(false, target1.runtimeEquals(source));
    Assertions.assertEquals(false, target2.runtimeEquals(source));
    Assertions.assertEquals(false, target3.runtimeEquals(source));
    Assertions.assertEquals(true, target1.runtimeEquals(target1));
    Assertions.assertEquals(false, target2.runtimeEquals(target1));
    Assertions.assertEquals(false, target3.runtimeEquals(target1));
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
    Assertions.assertEquals(DiffValueString.of(source.getValue(), NULL.getValue()).diff(), source.runtimeDiff(NULL).diff());
    Assertions.assertEquals(DiffValueString.of(source.getValue(), target1.getValue()).diff(), source.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(source.getValue(), target2.getValue()).diff(), source.runtimeDiff(target2).diff());
    Assertions.assertEquals(DiffValueString.of(source.getValue(), target3.getValue()).diff(), source.runtimeDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(target1.getValue(), target2.getValue()).diff(), target1.runtimeDiff(target2).diff());
    Assertions.assertEquals(DiffValueString.of(target1.getValue(), target3.getValue()).diff(), target1.runtimeDiff(target3).diff());
    Assertions.assertEquals(DiffValueString.of(target2.getValue(), target3.getValue()).diff(), target2.runtimeDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(NULL).diff());
    Assertions.assertEquals(DiffValueString.of(NULL.getValue(), source.getValue()).diff(), NULL.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueString.of(target1.getValue(), source.getValue()).diff(), target1.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueString.of(target2.getValue(), source.getValue()).diff(), target2.runtimeDiff(source).diff());
    Assertions.assertEquals(DiffValueString.of(target3.getValue(), source.getValue()).diff(), target3.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(target2.getValue(), target1.getValue()).diff(), target2.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(target3.getValue(), target1.getValue()).diff(), target3.runtimeDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(target3.getValue(), target2.getValue()).diff(), target3.runtimeDiff(target2).diff());
  }

  @Test
  void test4() {
    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueString.of(source.getValue(), NULL.getValue()).diff(), source.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueString.of(source.getValue(), target1.getValue()).diff(), source.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(source.getValue(), target2.getValue()).diff(), source.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueString.of(source.getValue(), target3.getValue()).diff(), source.absolutelyDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(target1.getValue(), target2.getValue()).diff(), target1.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueString.of(target1.getValue(), target3.getValue()).diff(), target1.absolutelyDiff(target3).diff());
    Assertions.assertEquals(DiffValueString.of(target2.getValue(), target3.getValue()).diff(), target2.absolutelyDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueString.of(NULL.getValue(), source.getValue()).diff(), NULL.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueString.of(target1.getValue(), source.getValue()).diff(), target1.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueString.of(target2.getValue(), source.getValue()).diff(), target2.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueString.of(target3.getValue(), source.getValue()).diff(), target3.absolutelyDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(target2.getValue(), target1.getValue()).diff(), target2.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(target3.getValue(), target1.getValue()).diff(), target3.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueString.of(target3.getValue(), target2.getValue()).diff(), target3.absolutelyDiff(target2).diff());
  }

  @Test
  void test5() {
    EnvContext.set("uat", "pro");
    try {
      String diffString = "{\"uat\":\"time\",\"pro\":\"date\"}";

      DiffValueString diff = target1.absolutelyDiff(source);
      System.out.println(diff);

      Assertions.assertEquals(diffString, diff.diff());

      DiffValueString diffValueString = JacksonUtils.toObj(diffString, new TypeReference<DiffValueString>() {
      });

      Assertions.assertEquals(diff, diffValueString);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeMissingKeys() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueString diff1 = JacksonUtils.toObj("{\"uat\":\"val\"}", new TypeReference<DiffValueString>() {});
      Assertions.assertEquals("val", diff1.getSource());
      Assertions.assertNull(diff1.getTarget());

      DiffValueString diff2 = JacksonUtils.toObj("{\"pro\":\"val\"}", new TypeReference<DiffValueString>() {});
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
    Assertions.assertEquals(source, ValueString.of("date"));
    Assertions.assertNotEquals(source, target1);
    Assertions.assertNotEquals(source, NULL);
    Assertions.assertEquals(source.hashCode(), ValueString.of("date").hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("date", source.toString());
    Assertions.assertEquals("time", target1.toString());
  }

  @Test
  void testSerializeNullValue() {
    String json = JacksonUtils.toStr((ValueString) null);
    Assertions.assertNull(json);
  }

  @Test
  void testSerializeValueWithNullContent() {
    String json = JacksonUtils.toStr(ValueString.of());
    Assertions.assertEquals("null", json);
  }

  @Test
  void testDeserializeNullJson() {
    ValueString result = JacksonUtils.toObj("null", new TypeReference<ValueString>() {});
    Assertions.assertEquals(ValueString.of(), result);
  }

  @Test
  void testDeserializeNullString() {
    ValueString result = JacksonUtils.toObj("\"null\"", new TypeReference<ValueString>() {});
    Assertions.assertEquals(ValueString.of(), result);
  }

  @Test
  void testDeserializeEmptyString() {
    ValueString result = JacksonUtils.toObj("\"\"", new TypeReference<ValueString>() {});
    Assertions.assertEquals(ValueString.of(), result);
  }

  @Test
  void testValueSerializeDeserializeRoundTrip() {
    ValueString original = ValueString.of("InnoDB");
    String json = JacksonUtils.toStr(original);
    Assertions.assertEquals("\"InnoDB\"", json);
    ValueString restored = JacksonUtils.toObj(json, new TypeReference<ValueString>() {});
    Assertions.assertEquals(original, restored);
  }

  @Test
  void testDiffSerializeNullSource() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueString diff = DiffValueString.of(null, "utf8");
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":null,\"pro\":\"utf8\"}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeNullTarget() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueString diff = DiffValueString.of("utf8", null);
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":\"utf8\",\"pro\":null}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeDeserializeRoundTrip() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueString original = DiffValueString.of("utf8mb4", "utf8mb3");
      String json = original.diff();
      Assertions.assertEquals("{\"uat\":\"utf8mb4\",\"pro\":\"utf8mb3\"}", json);
      DiffValueString restored = JacksonUtils.toObj(json, new TypeReference<DiffValueString>() {});
      Assertions.assertEquals(original, restored);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeJsonNull() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueString result = JacksonUtils.toObj("null", new TypeReference<DiffValueString>() {});
      Assertions.assertEquals(DiffValueString.NULL, result);
    } finally {
      EnvContext.remove();
    }
  }

}
