package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.ValueComment;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffValueComment;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValueComment {

  ValueComment source = ValueComment.of("date");

  ValueComment target1 = ValueComment.of("time");

  ValueComment target2 = ValueComment.of("TIME");

  ValueComment target3 = ValueComment.of(" TIME ");

  ValueComment NULL = ValueComment.of();

  @Test
  void test1() {
    Assertions.assertEquals(true, NULL.runtimeEquals(NULL));
    Assertions.assertEquals(true, source.runtimeEquals(NULL));
    Assertions.assertEquals(true, source.runtimeEquals(target1));
    Assertions.assertEquals(true, source.runtimeEquals(target2));
    Assertions.assertEquals(true, source.runtimeEquals(target3));
    Assertions.assertEquals(true, target1.runtimeEquals(target1));
    Assertions.assertEquals(true, target1.runtimeEquals(target2));
    Assertions.assertEquals(true, target1.runtimeEquals(target3));
    Assertions.assertEquals(true, target2.runtimeEquals(target3));

    Assertions.assertEquals(true, NULL.runtimeEquals(NULL));
    Assertions.assertEquals(true, NULL.runtimeEquals(source));
    Assertions.assertEquals(true, target1.runtimeEquals(source));
    Assertions.assertEquals(true, target2.runtimeEquals(source));
    Assertions.assertEquals(true, target3.runtimeEquals(source));
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
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(NULL).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target2).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target2).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target2.runtimeDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(NULL).diff());
    Assertions.assertEquals(Diff.EQUALS, NULL.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target2.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target3.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target2.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target3.runtimeDiff(target1).diff());
    Assertions.assertEquals(Diff.EQUALS, target3.runtimeDiff(target2).diff());
  }

  @Test
  void test4() {
    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueComment.of(source.getValue(), NULL.getValue()).diff(), source.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueComment.of(source.getValue(), target1.getValue()).diff(), source.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueComment.of(source.getValue(), target2.getValue()).diff(), source.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueComment.of(source.getValue(), target3.getValue()).diff(), source.absolutelyDiff(target3).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueComment.of(target1.getValue(), target2.getValue()).diff(), target1.absolutelyDiff(target2).diff());
    Assertions.assertEquals(DiffValueComment.of(target1.getValue(), target3.getValue()).diff(), target1.absolutelyDiff(target3).diff());
    Assertions.assertEquals(DiffValueComment.of(target2.getValue(), target3.getValue()).diff(), target2.absolutelyDiff(target3).diff());

    Assertions.assertEquals(Diff.EQUALS, NULL.absolutelyDiff(NULL).diff());
    Assertions.assertEquals(DiffValueComment.of(NULL.getValue(), source.getValue()).diff(), NULL.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueComment.of(target1.getValue(), source.getValue()).diff(), target1.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueComment.of(target2.getValue(), source.getValue()).diff(), target2.absolutelyDiff(source).diff());
    Assertions.assertEquals(DiffValueComment.of(target3.getValue(), source.getValue()).diff(), target3.absolutelyDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, target1.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueComment.of(target2.getValue(), target1.getValue()).diff(), target2.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueComment.of(target3.getValue(), target1.getValue()).diff(), target3.absolutelyDiff(target1).diff());
    Assertions.assertEquals(DiffValueComment.of(target3.getValue(), target2.getValue()).diff(), target3.absolutelyDiff(target2).diff());
  }

  @Test
  void test5() {
    EnvContext.set("uat", "pro");
    try {
      String diffString = "{\"uat\":\"time\",\"pro\":\"date\"}";

      DiffValueComment diff = target1.absolutelyDiff(source);
      System.out.println(diff);

      Assertions.assertEquals(diffString, diff.diff());

      DiffValueComment diffValueComment = JacksonUtils.toObj(diffString, new TypeReference<DiffValueComment>() {
      });

      Assertions.assertEquals(diff, diffValueComment);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeMissingKeys() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueComment diff1 = JacksonUtils.toObj("{\"uat\":\"'c'\"}", new TypeReference<DiffValueComment>() {});
      Assertions.assertEquals("'c'", diff1.getSource());
      Assertions.assertNull(diff1.getTarget());

      DiffValueComment diff2 = JacksonUtils.toObj("{\"pro\":\"'c'\"}", new TypeReference<DiffValueComment>() {});
      Assertions.assertNull(diff2.getSource());
      Assertions.assertEquals("'c'", diff2.getTarget());
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
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(null).diff());
  }

  @Test
  void testEqualsAndHashCode() {
    Assertions.assertEquals(source, ValueComment.of("date"));
    Assertions.assertNotEquals(source, target1);
    Assertions.assertNotEquals(source, NULL);
    Assertions.assertEquals(source.hashCode(), ValueComment.of("date").hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("date", source.toString());
    Assertions.assertEquals("time", target1.toString());
  }

  @Test
  void testSerializeNullValue() {
    String json = JacksonUtils.toStr((ValueComment) null);
    Assertions.assertNull(json);
  }

  @Test
  void testSerializeValueWithNullContent() {
    String json = JacksonUtils.toStr(ValueComment.of());
    Assertions.assertEquals("null", json);
  }

  @Test
  void testDeserializeNullJson() {
    ValueComment result = JacksonUtils.toObj("null", new TypeReference<ValueComment>() {});
    Assertions.assertEquals(ValueComment.of(), result);
  }

  @Test
  void testDeserializeNullString() {
    ValueComment result = JacksonUtils.toObj("\"null\"", new TypeReference<ValueComment>() {});
    Assertions.assertEquals(ValueComment.of(), result);
  }

  @Test
  void testDeserializeEmptyString() {
    ValueComment result = JacksonUtils.toObj("\"\"", new TypeReference<ValueComment>() {});
    Assertions.assertEquals(ValueComment.of(), result);
  }

  @Test
  void testValueSerializeDeserializeRoundTrip() {
    ValueComment original = ValueComment.of("'table comment'");
    String json = JacksonUtils.toStr(original);
    Assertions.assertEquals("\"'table comment'\"", json);
    ValueComment restored = JacksonUtils.toObj(json, new TypeReference<ValueComment>() {});
    Assertions.assertEquals(original, restored);
  }

  @Test
  void testDiffSerializeNullSource() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueComment diff = DiffValueComment.of(null, "'comment'");
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":null,\"pro\":\"'comment'\"}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeNullTarget() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueComment diff = DiffValueComment.of("'comment'", null);
      String json = diff.diff();
      Assertions.assertTrue(json.contains("\"uat\":\"'comment'\""));
      Assertions.assertTrue(json.contains("\"pro\":null"));
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeDeserializeRoundTrip() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueComment original = DiffValueComment.of("'a'", "'b'");
      String json = original.diff();
      Assertions.assertEquals("{\"uat\":\"'a'\",\"pro\":\"'b'\"}", json);
      DiffValueComment restored = JacksonUtils.toObj(json, new TypeReference<DiffValueComment>() {});
      Assertions.assertEquals(original, restored);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeJsonNull() {
    EnvContext.set("uat", "pro");
    try {
      DiffValueComment result = JacksonUtils.toObj("null", new TypeReference<DiffValueComment>() {});
      Assertions.assertEquals(DiffValueComment.NULL, result);
    } finally {
      EnvContext.remove();
    }
  }

}
