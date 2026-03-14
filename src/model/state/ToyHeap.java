package model.state;

import Exceptions.CustomException;
import Exceptions.InvalidAddressException;
import Exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class ToyHeap<Value> implements Heap<Integer, Value> {

  private final Map<Integer, Value> content;
  private int nextFreeAddress;

  public ToyHeap() {
    this.content = new HashMap<>();
    this.nextFreeAddress = 1;
  }

  @Override
  public int allocate(Value val) {
    content.put(nextFreeAddress, val);
    nextFreeAddress++;
    return nextFreeAddress - 1;
  }

  @Override
  public Value get(int address) {
    if (!content.containsKey(address)) {
      throw new InvalidAddressException("Address out of bounds");
    }
    return content.get(address);
  }

  @Override
  public Map<Integer, Value> getAll() {
    return new HashMap<>(content);
  }

  @Override
  public void put(Integer address, Value newVal) {
    content.put(address, newVal);
  }

  @Override
  public void setContent(Map<Integer, Value> content) {
    this.content.clear();
    this.content.putAll(content);
  }

  @Override
  public String toString() {
    StringBuilder answer = new StringBuilder("Heap:\n");
    try {
      for (int key : content.keySet()) {
        answer
            .append("{")
            .append(key)
            .append(":-> ")
            .append(content.get(key).toString())
            .append("}\n");
      }
    } catch (Exception exception) {
      throw new RuntimeException(exception.getMessage());
    }
    return answer.toString();
  }
}
