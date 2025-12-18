package cn.addenda.ddldiff.bo.diff;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.ddldiff.jackson.deserializer.diff.DiffTableIndexesDeserializer;
import cn.addenda.ddldiff.jackson.serializer.diff.DiffTableIndexesSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@JsonSerialize(using = DiffTableIndexesSerializer.class)
@JsonDeserialize(using = DiffTableIndexesDeserializer.class)
public class DiffTableIndexes implements Diff, Iterable<DiffTableIndex> {

  public static final DiffTableIndexes NULL = new DiffTableIndexes();

  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NullChecker.class)
  private List<DiffTableIndex> diffTableIndexList = new ArrayList<>();

  private DiffTableIndexes() {
  }

  @Override
  public String diff() {
    if (ifNull(this)) {
      return EQUALS;
    }
    return JacksonUtils.toStr(this);
  }

  public static DiffTableIndexes of(List<DiffTableIndex> diffTableIndexList) {
    if (diffTableIndexList == null || diffTableIndexList.isEmpty()) {
      return NULL;
    }
    DiffTableIndexes diffTableIndexes = new DiffTableIndexes();
    diffTableIndexes.diffTableIndexList.addAll(diffTableIndexList);
    return diffTableIndexes;
  }

  public static boolean ifNull(DiffTableIndexes diffTableIndexes) {
    return (null == diffTableIndexes) || (NULL == diffTableIndexes) || NULL.equals(diffTableIndexes);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiffTableIndexes that = (DiffTableIndexes) o;
    return Objects.equals(diffTableIndexList, that.diffTableIndexList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(diffTableIndexList.toArray(new Object[0]));
  }

  @Override
  public String toString() {
    return diff();
  }

  @Override
  public Iterator<DiffTableIndex> iterator() {
    return diffTableIndexList.iterator();
  }

}
