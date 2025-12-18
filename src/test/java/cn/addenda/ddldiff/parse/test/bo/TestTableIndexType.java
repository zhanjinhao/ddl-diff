package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.IndexType;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexType;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableIndexType {

  IndexType source = IndexType.UNIQUE;

  IndexType target1 = IndexType.SIMPLE;

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

}
