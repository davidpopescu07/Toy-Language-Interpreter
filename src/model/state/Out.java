package model.state;

import java.util.List;

public interface Out {
  void add(Object value);

  String toString();

  List<Object> getList();
}
