package model.state;

import Exceptions.InvalidAddressException;

import java.util.Map;

public interface Heap<Key, Value> {
  int allocate(Value val);

  Value get(int address);

  Map<Integer, Value> getAll();

  void setContent(Map<Integer, Value> content);

  void put(Key address, Value newVal);
}
