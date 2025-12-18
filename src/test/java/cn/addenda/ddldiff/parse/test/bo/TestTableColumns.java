package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.bo.*;
import cn.addenda.ddldiff.bo.diff.Diff;
import cn.addenda.ddldiff.bo.diff.DiffTableColumns;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableColumns {

  TableColumn source = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target1 = TableColumn.builder()
          .name(ValueName.of(" `AGE` "))
          .type(ValueType.of("int"))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn target2 = TableColumn.builder()
          .name(ValueName.of("age"))
          .type(ValueType.of(" INT "))
          .charset(ValueString.of("utf8mb4"))
          .collate(ValueString.of("utf8mb4_general_ci"))
          .defaultValue(ValueString.of("0"))
          .ifNullable(ValueBoolean.FALSE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("年龄"))
          .build();

  TableColumn targetN = TableColumn.builder()
          .name(ValueName.of("name"))
          .type(ValueType.of("varchar(20)"))
          .charset(ValueString.of("utf8mb3"))
          .collate(ValueString.of("utf8mb3_general_ci"))
          .ifNullable(ValueBoolean.TRUE)
          .ifAutoIncrement(ValueBoolean.FALSE)
          .comment(ValueComment.of("姓名"))
          .build();

  @Test
  void test1_1() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2, targetN);
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1, source);

    Assertions.assertEquals(true, tableColumns1.runtimeEquals(tableColumns2));
  }

  @Test
  void test1_2() {
    TableColumns tableColumns1 = TableColumns.of(source);
    TableColumns tableColumns2 = TableColumns.of(targetN);

    Assertions.assertEquals(false, tableColumns1.runtimeEquals(tableColumns2));
  }

  @Test
  void test1_3() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2);
    TableColumns tableColumns2 = TableColumns.of();

    Assertions.assertEquals(false, tableColumns1.runtimeEquals(tableColumns2));
  }

  @Test
  void test1_4() {
    TableColumns tableColumns1 = TableColumns.of();
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1);

    Assertions.assertEquals(false, tableColumns1.runtimeEquals(tableColumns2));
  }

  @Test
  void test1_5() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2);
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1);

    Assertions.assertEquals(false, tableColumns1.runtimeEquals(tableColumns2));
  }

  @Test
  void test1_6() {
    TableColumns tableColumns1 = TableColumns.of(source);
    TableColumns tableColumns2 = TableColumns.of(target1);

    Assertions.assertEquals(true, tableColumns1.runtimeEquals(tableColumns2));
  }

  @Test
  void test2_1() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2, targetN);
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1, source);

    Assertions.assertEquals(Diff.EQUALS, tableColumns1.runtimeDiff(tableColumns2).diff());
  }

  @Test
  void test2_2() {
    TableColumns tableColumns1 = TableColumns.of(source);
    TableColumns tableColumns2 = TableColumns.of(targetN);

    Assertions.assertEquals(
            DiffTableColumns.of(ArrayUtils.asArrayList(
                    source.runtimeDiff(TableColumn.of()),
                    TableColumn.of().runtimeDiff(targetN))),
            tableColumns1.runtimeDiff(tableColumns2));
  }

  @Test
  void test2_3() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2);
    TableColumns tableColumns2 = TableColumns.of();

    Assertions.assertEquals(DiffTableColumns.of(ArrayUtils.asArrayList(
            source.runtimeDiff(TableColumn.of()),
            target1.runtimeDiff(TableColumn.of()),
            target2.runtimeDiff(TableColumn.of()))), tableColumns1.runtimeDiff(tableColumns2));
  }

  @Test
  void test2_4() {
    TableColumns tableColumns1 = TableColumns.of();
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1);

    Assertions.assertEquals(DiffTableColumns.of(ArrayUtils.asArrayList(
            TableColumn.of().runtimeDiff(targetN),
            TableColumn.of().runtimeDiff(target2),
            TableColumn.of().runtimeDiff(target1))), tableColumns1.runtimeDiff(tableColumns2));
  }

  @Test
  void test2_5() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2);
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1);

    Assertions.assertEquals(
            DiffTableColumns.of(ArrayUtils.asArrayList(
                    target2.runtimeDiff(TableColumn.of()),
                    TableColumn.of().runtimeDiff(targetN))),
            tableColumns1.runtimeDiff(tableColumns2));
  }

  @Test
  void test2_6() {
    TableColumns tableColumns1 = TableColumns.of(source);
    TableColumns tableColumns2 = TableColumns.of(target1);

    Assertions.assertEquals(Diff.EQUALS, tableColumns1.runtimeDiff(tableColumns2).diff());
  }


  @Test
  void test3_0() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2, targetN);
    TableColumns tableColumns2 = TableColumns.of(source, target1, target2, targetN);

    Assertions.assertEquals(true, tableColumns1.absolutelyEquals(tableColumns2));
  }


  @Test
  void test3_1() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2, targetN);
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1, source);

    Assertions.assertEquals(false, tableColumns1.absolutelyEquals(tableColumns2));
  }

  @Test
  void test3_2() {
    TableColumns tableColumns1 = TableColumns.of(source);
    TableColumns tableColumns2 = TableColumns.of(targetN);

    Assertions.assertEquals(false, tableColumns1.absolutelyEquals(tableColumns2));
  }

  @Test
  void test3_3() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2);
    TableColumns tableColumns2 = TableColumns.of();

    Assertions.assertEquals(false, tableColumns1.absolutelyEquals(tableColumns2));
  }

  @Test
  void test3_4() {
    TableColumns tableColumns1 = TableColumns.of();
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1);

    Assertions.assertEquals(false, tableColumns1.absolutelyEquals(tableColumns2));
  }

  @Test
  void test3_5() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2);
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1);

    Assertions.assertEquals(false, tableColumns1.absolutelyEquals(tableColumns2));
  }

  @Test
  void test3_6() {
    TableColumns tableColumns1 = TableColumns.of(source);
    TableColumns tableColumns2 = TableColumns.of(target1);

    Assertions.assertEquals(false, tableColumns1.absolutelyEquals(tableColumns2));
  }

  @Test
  void test4_0() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2, targetN);
    TableColumns tableColumns2 = TableColumns.of(source, target1, target2, targetN);

    Assertions.assertEquals(Diff.EQUALS, tableColumns1.absolutelyDiff(tableColumns2).diff());
  }


  @Test
  void test4_1() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2, targetN);
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1, source);

    DiffTableColumns diffTableColumns = DiffTableColumns.of(ArrayUtils.asArrayList(source.absolutelyDiff(targetN),
            target1.absolutelyDiff(target2),
            target2.absolutelyDiff(target1), targetN.absolutelyDiff(source)));

    DiffTableColumns diff = tableColumns1.absolutelyDiff(tableColumns2);
    System.out.println(diff);
    Assertions.assertEquals(diffTableColumns.diff(), diff.diff());
  }

  @Test
  void test4_2() {
    TableColumns tableColumns1 = TableColumns.of(source);
    TableColumns tableColumns2 = TableColumns.of(targetN);

    DiffTableColumns diffTableColumns = DiffTableColumns.of(ArrayUtils.asArrayList(source.absolutelyDiff(targetN)));

    Assertions.assertEquals(diffTableColumns.diff(), tableColumns1.absolutelyDiff(tableColumns2).diff());
  }

  @Test
  void test4_3() {
    TableColumns tableColumns1 = TableColumns.of(source, target1, target2);
    TableColumns tableColumns2 = TableColumns.of();

    DiffTableColumns diffTableColumns = DiffTableColumns.of(ArrayUtils.asArrayList(source.absolutelyDiff(TableColumn.of()),
            target1.absolutelyDiff(TableColumn.of()),
            target2.absolutelyDiff(TableColumn.of())));

    Assertions.assertEquals(diffTableColumns.diff(), tableColumns1.absolutelyDiff(tableColumns2).diff());
  }

  @Test
  void test4_4() {
    TableColumns tableColumns1 = TableColumns.of();
    TableColumns tableColumns2 = TableColumns.of(targetN, target2, target1);

    DiffTableColumns diffTableColumns = DiffTableColumns.of(ArrayUtils.asArrayList(TableColumn.of().absolutelyDiff(targetN),
            TableColumn.of().absolutelyDiff(target2),
            TableColumn.of().absolutelyDiff(target1)));

    Assertions.assertEquals(diffTableColumns.diff(), tableColumns1.absolutelyDiff(tableColumns2).diff());
  }

  @Test
  void test4_5() {
    TableColumns tableColumns1 = TableColumns.of(source);
    TableColumns tableColumns2 = TableColumns.of(source, targetN);

    DiffTableColumns diffTableColumns = DiffTableColumns.of(ArrayUtils.asArrayList(TableColumn.of().absolutelyDiff(targetN)));

    DiffTableColumns diff = tableColumns1.absolutelyDiff(tableColumns2);
    System.out.println(diff);
    Assertions.assertEquals(diffTableColumns.diff(), diff.diff());
  }

  @Test
  void test4_6() {
    TableColumns tableColumns1 = TableColumns.of(source, targetN);
    TableColumns tableColumns2 = TableColumns.of(source);

    DiffTableColumns diffTableColumns = DiffTableColumns.of(ArrayUtils.asArrayList(targetN.absolutelyDiff(TableColumn.of())));

    DiffTableColumns diff = tableColumns1.absolutelyDiff(tableColumns2);
    System.out.println(diff);
    Assertions.assertEquals(diffTableColumns.diff(), diff.diff());
  }


  @Test
  void test5() {
    String diffString = "[{\"columnName\":\"age(source)-name(target)\",\"diffName\":{\"source\":\"age\",\"target\":\"name\"},\"diffType\":{\"source\":\"int\",\"target\":\"varchar(20)\"},\"diffCharset\":{\"source\":\"utf8mb4\",\"target\":\"utf8mb3\"},\"diffCollate\":{\"source\":\"utf8mb4_general_ci\",\"target\":\"utf8mb3_general_ci\"},\"diffDefaultValue\":{\"source\":\"0\",\"target\":null},\"diffIfNullable\":{\"source\":false,\"target\":true},\"diffComment\":{\"source\":\"年龄\",\"target\":\"姓名\"}},{\"columnName\":\" `AGE` (source)-age(target)\",\"diffName\":{\"source\":\" `AGE` \",\"target\":\"age\"},\"diffType\":{\"source\":\"int\",\"target\":\" INT \"}},{\"columnName\":\"age(source)- `AGE` (target)\",\"diffName\":{\"source\":\"age\",\"target\":\" `AGE` \"},\"diffType\":{\"source\":\" INT \",\"target\":\"int\"}},{\"columnName\":\"name(source)-age(target)\",\"diffName\":{\"source\":\"name\",\"target\":\"age\"},\"diffType\":{\"source\":\"varchar(20)\",\"target\":\"int\"},\"diffCharset\":{\"source\":\"utf8mb3\",\"target\":\"utf8mb4\"},\"diffCollate\":{\"source\":\"utf8mb3_general_ci\",\"target\":\"utf8mb4_general_ci\"},\"diffDefaultValue\":{\"source\":null,\"target\":\"0\"},\"diffIfNullable\":{\"source\":true,\"target\":false},\"diffComment\":{\"source\":\"姓名\",\"target\":\"年龄\"}}]";

    DiffTableColumns diffTableColumns = JacksonUtils.toObj(diffString, new TypeReference<DiffTableColumns>() {
    });

    System.out.println(diffTableColumns);

    Assertions.assertEquals(JacksonUtils.formatJson(diffString), JacksonUtils.formatJson(diffTableColumns.diff()));
  }

}
