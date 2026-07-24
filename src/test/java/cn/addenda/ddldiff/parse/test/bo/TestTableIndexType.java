package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.IndexType;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableIndexType {

  IndexType source = IndexType.UNIQUE;

  IndexType target1 = IndexType.SIMPLE;

  // ================================================================
  //              DiffTableIndexType.of
  // ================================================================

  @Test
  void testOfSameEnum() {
    Assertions.assertEquals(DiffTableIndexType.NULL, DiffTableIndexType.of(IndexType.PRIMARY, IndexType.PRIMARY));
    Assertions.assertEquals(DiffTableIndexType.NULL, DiffTableIndexType.of(IndexType.UNIQUE, IndexType.UNIQUE));
    Assertions.assertEquals(DiffTableIndexType.NULL, DiffTableIndexType.of(IndexType.SIMPLE, IndexType.SIMPLE));
  }

  @Test
  void testOfDifferentEnum() {
    Assertions.assertEquals(IndexType.UNIQUE, DiffTableIndexType.of(source, target1).getSource());
    Assertions.assertEquals(IndexType.SIMPLE, DiffTableIndexType.of(source, target1).getTarget());
  }

  @Test
  void testOfNullSource() {
    DiffTableIndexType diff = DiffTableIndexType.of(null, IndexType.PRIMARY);
    Assertions.assertNull(diff.getSource());
    Assertions.assertEquals(IndexType.PRIMARY, diff.getTarget());
  }

  @Test
  void testOfNullTarget() {
    DiffTableIndexType diff = DiffTableIndexType.of(IndexType.PRIMARY, null);
    Assertions.assertEquals(IndexType.PRIMARY, diff.getSource());
    Assertions.assertNull(diff.getTarget());
  }

  @Test
  void testOfBothNull() {
    Assertions.assertEquals(DiffTableIndexType.NULL, DiffTableIndexType.of(null, null));
  }

  // ================================================================
  //              ifNull / equals / hashCode / toString
  // ================================================================

  @Test
  void testIfNull() {
    Assertions.assertTrue(DiffTableIndexType.ifNull(null));
    Assertions.assertTrue(DiffTableIndexType.ifNull(DiffTableIndexType.NULL));
    Assertions.assertFalse(DiffTableIndexType.ifNull(DiffTableIndexType.of(source, target1)));
  }

  @Test
  void testEqualsAndHashCode() {
    DiffTableIndexType d1 = DiffTableIndexType.of(IndexType.UNIQUE, IndexType.SIMPLE);
    DiffTableIndexType d2 = DiffTableIndexType.of(IndexType.UNIQUE, IndexType.SIMPLE);
    DiffTableIndexType d3 = DiffTableIndexType.of(IndexType.PRIMARY, IndexType.UNIQUE);
    Assertions.assertEquals(d1, d2);
    Assertions.assertNotEquals(d1, d3);
    Assertions.assertEquals(d1.hashCode(), d2.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexType.NULL.toString());
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexType.of(source, source).toString());
  }

  // ================================================================
  //              序列化 / 反序列化
  // ================================================================

  @Test
  void test1() {
    EnvContext.set("uat", "pro");
    try {
      String diffString = "{\"uat\":\"UNIQUE\",\"pro\":\"SIMPLE\"}";
      DiffTableIndexType diff = DiffTableIndexType.of(source, target1);
      System.out.println(diff);

      Assertions.assertEquals(diffString, diff.diff());

      DiffTableIndexType diffTableIndexType = JacksonUtils.toObj(diffString, new TypeReference<DiffTableIndexType>() {
      });
      Assertions.assertEquals(diff, diffTableIndexType);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeJsonNull() {
    EnvContext.set("uat", "pro");
    try {
      DiffTableIndexType result = JacksonUtils.toObj("null", new TypeReference<DiffTableIndexType>() {
      });
      Assertions.assertEquals(DiffTableIndexType.NULL, result);

      DiffTableIndexType result2 = JacksonUtils.toObj("\"\"", new TypeReference<DiffTableIndexType>() {
      });
      Assertions.assertEquals(DiffTableIndexType.NULL, result2);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeMissingKeys() {
    EnvContext.set("uat", "pro");
    try {
      DiffTableIndexType diff1 = JacksonUtils.toObj("{\"uat\":\"PRIMARY\"}", new TypeReference<DiffTableIndexType>() {
      });
      Assertions.assertEquals(IndexType.PRIMARY, diff1.getSource());
      Assertions.assertNull(diff1.getTarget());

      DiffTableIndexType diff2 = JacksonUtils.toObj("{\"pro\":\"UNIQUE\"}", new TypeReference<DiffTableIndexType>() {
      });
      Assertions.assertNull(diff2.getSource());
      Assertions.assertEquals(IndexType.UNIQUE, diff2.getTarget());
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffDeserializeInvalidEnumThrows() {
    EnvContext.set("uat", "pro");
    try {
      IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
              () -> JacksonUtils.toObj("{\"uat\":\"INVALID\"}", new TypeReference<DiffTableIndexType>() {
              }));
      Assertions.assertEquals("No enum constant cn.addenda.ddldiff.bo.IndexType.INVALID", illegalArgumentException.getMessage());
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeNullSource() {
    EnvContext.set("uat", "pro");
    try {
      DiffTableIndexType diff = DiffTableIndexType.of(null, IndexType.PRIMARY);
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":null,\"pro\":\"PRIMARY\"}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeNullTarget() {
    EnvContext.set("uat", "pro");
    try {
      DiffTableIndexType diff = DiffTableIndexType.of(IndexType.PRIMARY, null);
      String json = diff.diff();
      Assertions.assertEquals("{\"uat\":\"PRIMARY\",\"pro\":null}", json);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDiffSerializeDeserializeRoundTrip() {
    EnvContext.set("uat", "pro");
    try {
      DiffTableIndexType original = DiffTableIndexType.of(IndexType.PRIMARY, IndexType.UNIQUE);
      String json = original.diff();
      Assertions.assertEquals("{\"uat\":\"PRIMARY\",\"pro\":\"UNIQUE\"}", json);
      DiffTableIndexType restored = JacksonUtils.toObj(json, new TypeReference<DiffTableIndexType>() {
      });
      Assertions.assertEquals(original, restored);
    } finally {
      EnvContext.remove();
    }
  }

  @Test
  void testDeserializeEqualsRoundTrip() {
    Assertions.assertEquals(Diff.EQUALS, DiffTableIndexType.NULL.diff());
    DiffTableIndexType restored = JacksonUtils.toObj(Diff.EQUALS, new TypeReference<DiffTableIndexType>() {});
    Assertions.assertTrue(DiffTableIndexType.ifNull(restored));
  }

  @Test
  void testDeserializeInvalidStringThrows() {
    MismatchedInputException e = Assertions.assertThrows(MismatchedInputException.class,
            () -> JacksonUtils.toObj("\"foobar\"", new TypeReference<DiffTableIndexType>() {}));
    Assertions.assertTrue(e.getMessage().contains("Can not deserialize \"foobar\" to class cn.addenda.ddldiff.bo.diff.DiffTableIndexType"));
  }

}
