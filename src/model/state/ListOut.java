package model.state;

import java.util.ArrayList;
import java.util.List;

public class ListOut implements Out {
  @Override
  public String toString() {
    if (outputList.isEmpty()) return "[]";
    return outputList.toString();
  }

  private final List<Object> outputList;

  public ListOut() {
    outputList = new ArrayList<>();
  }

  @Override
  public void add(Object value) {
    outputList.add(value);
  }

  public List<Object> getList() {
    return new ArrayList<>(outputList); // return a copy
  }
}
