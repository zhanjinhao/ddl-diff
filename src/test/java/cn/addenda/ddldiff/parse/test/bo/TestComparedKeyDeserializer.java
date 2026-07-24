package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.diff.ComparedKey;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestComparedKeyDeserializer {

  @Test
  void testDeserializeValid() {
    ComparedKey result = JacksonUtils.toObj("\"id(source)-id(target)\"",
            new TypeReference<ComparedKey>() {
            });
    Assertions.assertEquals("id", result.getSelf());
    Assertions.assertEquals("id", result.getThat());
  }

  @Test
  void testDeserializeDifferentNames() {
    ComparedKey result = JacksonUtils.toObj("\"col_uat(env1)-col_pro(env2)\"",
            new TypeReference<ComparedKey>() {
            });
    Assertions.assertEquals("col_uat", result.getSelf());
    Assertions.assertEquals("col_pro", result.getThat());
  }

  @Test
  void testDeserializeBacktickNames() {
    ComparedKey result = JacksonUtils.toObj("\"`col`(source)-`col`(target)\"",
            new TypeReference<ComparedKey>() {
            });
    Assertions.assertEquals("`col`", result.getSelf());
    Assertions.assertEquals("`col`", result.getThat());
  }

  @Test
  void testDeserializeNullReturnsNull() {
    ComparedKey result = JacksonUtils.toObj("null", new TypeReference<ComparedKey>() {
    });
    Assertions.assertNull(result);
  }

  @Test
  void testDeserializeNullStringThrows() {
    Assertions.assertThrows(IllegalArgumentException.class,
            () -> JacksonUtils.toObj("\"null\"", new TypeReference<ComparedKey>() {
            }));
  }

  @Test
  void testDeserializeEmptyStringThrows() {
    Assertions.assertThrows(IllegalArgumentException.class,
            () -> JacksonUtils.toObj("\"\"", new TypeReference<ComparedKey>() {
            }));
  }

  @Test
  void testDeserializeMissingDashThrows() {
    Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> JacksonUtils.toObj("\"id(source)\"", new TypeReference<ComparedKey>() {
            }));
  }

  @Test
  void testDeserializeMissingParenThrows() {
    Assertions.assertThrows(StringIndexOutOfBoundsException.class,
            () -> JacksonUtils.toObj("\"id-id\"", new TypeReference<ComparedKey>() {
            }));
  }

}
