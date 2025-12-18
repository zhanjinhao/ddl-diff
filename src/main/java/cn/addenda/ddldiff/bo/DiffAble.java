package cn.addenda.ddldiff.bo;

import cn.addenda.ddldiff.bo.diff.Diff;

import java.util.Optional;

public interface DiffAble<T extends DiffAble<T, D>, D extends Diff> {

  T deepClone();

  boolean absolutelyEquals(T that);

  boolean runtimeEquals(T that);

  D absolutelyDiff(T that);

  D runtimeDiff(T that);


  default <T1 extends DiffAble<T1, D1>, D1 extends Diff> T1 nullableDeepClone(T1 diffAble) {
    return Optional.ofNullable(diffAble).map(DiffAble::deepClone).orElse(null);
  }

  default <T1 extends DiffAble<T1, D1>, D1 extends Diff> boolean nullableAbsolutelyEquals(T1 diffAble, T1 that) {
    return (diffAble == that) || (diffAble != null && diffAble.absolutelyEquals(that));
  }

  default <T1 extends DiffAble<T1, D1>, D1 extends Diff> boolean nullableRuntimeEquals(T1 diffAble, T1 that) {
    return (diffAble == that) || (diffAble != null && diffAble.runtimeEquals(that));
  }

  default <T1 extends DiffAble<T1, D1>, D1 extends Diff> D1 nullableAbsolutelyDiff(T1 diffAble, T1 that) {
    return Optional.ofNullable(diffAble).map(a -> a.absolutelyDiff(that)).orElse(null);
  }

  default <T1 extends DiffAble<T1, D1>, D1 extends Diff> D1 nullableRuntimeDiff(T1 diffAble, T1 that) {
    return Optional.ofNullable(diffAble).map(a -> a.runtimeDiff(that)).orElse(null);
  }

}
