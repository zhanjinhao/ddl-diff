package cn.addenda.ddldiff.parse.test.bo;

import cn.addenda.ddldiff.bo.*;
import cn.addenda.ddldiff.bo.diff.ComparedKey;
import cn.addenda.ddldiff.bo.diff.DiffTableIndex;
import cn.addenda.ddldiff.bo.diff.DiffTableIndexType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTableIndex {

  TableIndexColumns ageName1 = TableIndexColumns.of(
          TableIndexColumn.builder().name(ValueName.of("age")).order(ValueOrder.of()).build(),
          TableIndexColumn.builder().name(ValueName.of("name")).order(ValueOrder.of("desc")).build());

  TableIndexColumns ageName2 = TableIndexColumns.of(
          TableIndexColumn.builder().name(ValueName.of("age")).order(ValueOrder.of("asc")).build(),
          TableIndexColumn.builder().name(ValueName.of("`NAME`")).order(ValueOrder.of(" DESC ")).build());

  TableIndexColumns ageName3 = TableIndexColumns.of(
          TableIndexColumn.builder().name(ValueName.of("age")).order(ValueOrder.of("desc")).build(),
          TableIndexColumn.builder().name(ValueName.of("`NAME`")).order(ValueOrder.of(" asc ")).build());

  TableIndex source = TableIndex.builder()
          .tableIndexColumns(ageName1)
          .name(ValueName.of("ageName"))
          .indexType(IndexType.SIMPLE)
          .comment(ValueComment.of("a"))
          .build();

  TableIndex target1 = TableIndex.builder()
          .tableIndexColumns(ageName2)
          .name(ValueName.of("ageName"))
          .indexType(IndexType.UNIQUE)
          .comment(ValueComment.of("as"))
          .build();

  TableIndex target2 = TableIndex.builder()
          .tableIndexColumns(ageName3)
          .name(ValueName.of("ageName"))
          .indexType(IndexType.UNIQUE)
          .comment(ValueComment.of("asd"))
          .build();

  TableIndex target3 = TableIndex.builder()
          .tableIndexColumns(ageName2)
          .name(ValueName.of("ageName"))
          .indexType(IndexType.UNIQUE)
          .comment(ValueComment.of("asd"))
          .build();

  @Test
  void test1() {
    Assertions.assertEquals(true, source.runtimeEquals(source));
    Assertions.assertEquals(false, source.runtimeEquals(target1));
    Assertions.assertEquals(false, source.runtimeEquals(target2));
    Assertions.assertEquals(false, target1.runtimeEquals(target2));
    Assertions.assertEquals(true, target1.runtimeEquals(target3));
  }

  @Test
  void test2() {
    System.out.println(source.runtimeDiff(source).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(source.getName(), target1.getName()),
                    null, null,
                    DiffTableIndexType.of(source.getIndexType(), target1.getIndexType()), null).diff(),
            source.runtimeDiff(target1).diff());
    System.out.println(source.runtimeDiff(target1).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(source.getName(), target2.getName()),
                    source.getTableIndexColumns().runtimeDiff(target2.getTableIndexColumns()), null,
                    DiffTableIndexType.of(source.getIndexType(), target2.getIndexType()), null).diff(),
            source.runtimeDiff(target2).diff());
    System.out.println(source.runtimeDiff(target2).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(target1.getName(), target2.getName()),
                    target1.getTableIndexColumns().runtimeDiff(target2.getTableIndexColumns()), null,
                    null, null).diff(),
            target1.runtimeDiff(target2).diff());
    System.out.println(target1.runtimeDiff(target2).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(target1.getName(), target3.getName()),
                    target1.getTableIndexColumns().runtimeDiff(target3.getTableIndexColumns()), null,
                    null, null).diff(),
            target1.runtimeDiff(target3).diff());
    System.out.println(target1.runtimeDiff(target3).diff());
  }

  @Test
  void test3() {
    Assertions.assertEquals(true, source.absolutelyEquals(source));
    Assertions.assertEquals(false, source.absolutelyEquals(target1));
    Assertions.assertEquals(false, source.absolutelyEquals(target2));
    Assertions.assertEquals(false, target1.absolutelyEquals(target2));
    Assertions.assertEquals(false, target1.absolutelyEquals(target3));
  }

  @Test
  void test4() {
    System.out.println(source.absolutelyDiff(source).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(source.getName(), target1.getName()),
                    source.getTableIndexColumns().absolutelyDiff(target1.getTableIndexColumns()), source.getName().absolutelyDiff(target1.getName()),
                    DiffTableIndexType.of(source.getIndexType(), target1.getIndexType()), source.getComment().absolutelyDiff(target1.getComment())).diff(),
            source.absolutelyDiff(target1).diff());
    System.out.println(source.absolutelyDiff(target1).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(source.getName(), target2.getName()),
                    source.getTableIndexColumns().absolutelyDiff(target2.getTableIndexColumns()), source.getName().absolutelyDiff(target2.getName()),
                    DiffTableIndexType.of(source.getIndexType(), target2.getIndexType()), source.getComment().absolutelyDiff(target2.getComment())).diff(),
            source.absolutelyDiff(target2).diff());
    System.out.println(source.absolutelyDiff(target2).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(target1.getName(), target2.getName()),
                    target1.getTableIndexColumns().absolutelyDiff(target2.getTableIndexColumns()), target1.getName().absolutelyDiff(target2.getName()),
                    DiffTableIndexType.of(target1.getIndexType(), target2.getIndexType()), target1.getComment().absolutelyDiff(target2.getComment())).diff(),
            target1.absolutelyDiff(target2).diff());
    System.out.println(target1.absolutelyDiff(target2).diff());

    Assertions.assertEquals(
            DiffTableIndex.of(ComparedKey.of(target1.getName(), target3.getName()),
                    target1.getTableIndexColumns().absolutelyDiff(target3.getTableIndexColumns()), target1.getName().absolutelyDiff(target3.getName()),
                    DiffTableIndexType.of(target1.getIndexType(), target2.getIndexType()), target1.getComment().absolutelyDiff(target3.getComment())).diff(),
            target1.absolutelyDiff(target3).diff());
    System.out.println(target1.absolutelyDiff(target3).diff());
  }

}
