package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.TableIndexColumn;
import cn.addenda.ddldiff.bo.TableIndexColumns;
import cn.addenda.ddldiff.bo.ValueName;
import cn.addenda.ddldiff.bo.ValueOrder;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexColumns;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableIndexColumns {

  TableIndexColumn source1 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" asc "))
          .build();

  TableIndexColumn target1 = TableIndexColumn.builder()
          .name(ValueName.of("age"))
          .order(ValueOrder.of(" ASC "))
          .build();

  TableIndexColumn source2 = TableIndexColumn.builder()
          .name(ValueName.of("name"))
          .order(ValueOrder.of(" asc "))
          .build();

  TableIndexColumn target2 = TableIndexColumn.builder()
          .name(ValueName.of("name"))
          .order(ValueOrder.of(null))
          .build();

  TableIndexColumn source3 = TableIndexColumn.builder()
          .name(ValueName.of("grade"))
          .order(ValueOrder.of("desc"))
          .build();

  TableIndexColumn target3 = TableIndexColumn.builder()
          .name(ValueName.of("grade"))
          .order(ValueOrder.of(" DESC "))
          .build();

  TableIndexColumn source4 = TableIndexColumn.builder()
          .name(ValueName.of("class"))
          .order(ValueOrder.of("desc"))
          .build();

  TableIndexColumn target4 = TableIndexColumn.builder()
          .name(ValueName.of("class"))
          .order(ValueOrder.of(null))
          .build();

  @Test
  void test1_1() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3);

    Assertions.assertEquals(true, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test1_2() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    Assertions.assertEquals(false, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test1_3() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of();

    Assertions.assertEquals(false, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test1_4() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of();
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    Assertions.assertEquals(false, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test1_5() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target4, target3, target2, target1);

    Assertions.assertEquals(false, tableIndexColumns1.runtimeEquals(tableIndexColumns2));
  }

  @Test
  void test2_1() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3);

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(Diff.EQUALS, diff.diff());
  }

  @Test
  void test2_2() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"age  ASC , name, grade  DESC , class\"}",
            diff.diff());
  }

  @Test
  void test2_3() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of();

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":null}",
            diff.diff());
  }

  @Test
  void test2_4() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of();
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":null,\"target\":\"age  ASC , name, grade  DESC , class\"}",
            diff.diff());
  }

  @Test
  void test2_5() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target4, target3, target2, target1);

    DiffTableIndexColumns diff = tableIndexColumns1.runtimeDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"class, grade  DESC , name, age  ASC \"}",
            diff.diff());
  }

  @Test
  void test3_1() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3);

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test3_2() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test3_3() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of();

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test3_4() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of();
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test3_5() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target4, target3, target2, target1);

    Assertions.assertEquals(false, tableIndexColumns1.absolutelyEquals(tableIndexColumns2));
  }

  @Test
  void test4_1() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3);

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc\",\"target\":\"age  ASC , name, grade  DESC \"}",
            diff.diff());
  }

  @Test
  void test4_2() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"age  ASC , name, grade  DESC , class\"}",
            diff.diff());
  }

  @Test
  void test4_3() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of();

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":null}",
            diff.diff());
  }

  @Test
  void test4_4() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of();
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target1, target2, target3, target4);

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":null,\"target\":\"age  ASC , name, grade  DESC , class\"}",
            diff.diff());
  }

  @Test
  void test4_5() {
    TableIndexColumns tableIndexColumns1 = TableIndexColumns.of(source1, source2, source3, source4);
    TableIndexColumns tableIndexColumns2 = TableIndexColumns.of(target4, target3, target2, target1);

    DiffTableIndexColumns diff = tableIndexColumns1.absolutelyDiff(tableIndexColumns2);
    System.out.println(diff);
    Assertions.assertEquals(
            "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"class, grade  DESC , name, age  ASC \"}",
            diff.diff());
  }

  @Test
  void test5_1() {
    String diffString = "{\"source\":\"age  asc , name  asc , grade desc, class desc\",\"target\":\"class, grade  DESC , name, age  ASC \"}";
    TypeReference<DiffTableIndexColumns> typeReference = new TypeReference<DiffTableIndexColumns>() {
    };

    DiffTableIndexColumns diffTableIndexColumns = JacksonUtils.toObj(diffString, typeReference);

    String diffString2 = JacksonUtils.toStr(diffTableIndexColumns);

    System.out.println(diffString2);

    Assertions.assertEquals(diffString.replaceAll("\\s+", ""), diffString2.replaceAll("\\s+", ""));
  }

}
