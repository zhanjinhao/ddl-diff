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

}
