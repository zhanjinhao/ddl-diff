package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.ddldiff.bo.EnvContext;
import cn.addenda.ddldiff.bo.TableIndexColumn;
import cn.addenda.ddldiff.bo.ValueName;
import cn.addenda.ddldiff.bo.ValueOrder;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableIndexColumn {

  TableIndexColumn source = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" asc "))
          .build();

  TableIndexColumn target1 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" ASC "))
          .build();

  TableIndexColumn target2 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of("desc"))
          .build();

  TableIndexColumn target3 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" DESC "))
          .build();

  TableIndexColumn targetN = TableIndexColumn.builder()
          .name(ValueName.of("name"))
          .order(ValueOrder.of(" DESC "))
          .build();

  @Test
  void test1() {
    Assertions.assertEquals(true, source.runtimeEquals(source));
    Assertions.assertEquals(true, source.runtimeEquals(target1));
    Assertions.assertEquals(false, source.runtimeEquals(target2));
    Assertions.assertEquals(false, source.runtimeEquals(target3));
    Assertions.assertEquals(false, source.runtimeEquals(targetN));
  }

  @Test
  void test2() {
    Assertions.assertEquals(true, source.absolutelyEquals(source));
    Assertions.assertEquals(false, source.absolutelyEquals(target1));
    Assertions.assertEquals(false, source.absolutelyEquals(target2));
    Assertions.assertEquals(false, source.absolutelyEquals(target3));
    Assertions.assertEquals(false, source.absolutelyEquals(targetN));
  }

  @Test
  void test3() {
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(source).diff());
    Assertions.assertEquals(Diff.EQUALS, source.runtimeDiff(target1).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().runtimeDiff(target2.getOrder())).diff(),
            source.runtimeDiff(target2).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().runtimeDiff(target3.getOrder())).diff(),
            source.runtimeDiff(target3).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(source.getName().runtimeDiff(targetN.getName()), source.getOrder().runtimeDiff(targetN.getOrder())).diff(),
            source.runtimeDiff(targetN).diff());
  }

  @Test
  void test4() {
    Assertions.assertEquals(Diff.EQUALS, source.absolutelyDiff(source).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().absolutelyDiff(target1.getOrder())).diff(),
            source.absolutelyDiff(target1).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().absolutelyDiff(target2.getOrder())).diff(),
            source.absolutelyDiff(target2).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(null, source.getOrder().absolutelyDiff(target3.getOrder())).diff(),
            source.absolutelyDiff(target3).diff());
    Assertions.assertEquals(
            DiffTableIndexColumn.of(source.getName().absolutelyDiff(targetN.getName()), source.getOrder().absolutelyDiff(targetN.getOrder())).diff(),
            source.absolutelyDiff(targetN).diff());
  }

  @Test
  void test5() {
    EnvContext.set("uat", "pro");
    try {
      String diff = source.absolutelyDiff(targetN).diff();
      System.out.println(diff);
      Assertions.assertEquals("{\"diffName\":{\"uat\":\"age\",\"pro\":\"name\"},\"diffOrder\":{\"uat\":\" asc \",\"pro\":\" DESC \"}}", diff);
    } finally {
      EnvContext.remove();
    }
  }

}
