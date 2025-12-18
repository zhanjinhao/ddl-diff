package cn.addenda.ddldiff.bo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnvContext {

  public static final ThreadLocal<String> sourceName = ThreadLocal.withInitial(() -> "source");
  public static final ThreadLocal<String> targetName = ThreadLocal.withInitial(() -> "target");

  public static String getSourceName() {
    return sourceName.get();
  }

  public static String getTargetName() {
    return targetName.get();
  }

  public static void set(String source, String target) {
    sourceName.set(source);
    targetName.set(target);
  }

  public static void remove() {
    sourceName.remove();
    targetName.remove();
  }

}
