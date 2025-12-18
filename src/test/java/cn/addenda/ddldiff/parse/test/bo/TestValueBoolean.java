package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.ValueBoolean;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffValueBoolean;
import com.fasterxml.jackson.core.type.TypeReference;
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

}
