package model.state;

import Exceptions.CustomException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToySemaphoreTable implements SemaphoreTable<Integer, Integer, List<Integer>> {
  private Map<Integer, Pair<Integer, List<Integer>>> elements = new HashMap<>();
  private int nextFreeAddress = 1;

  @Override
  public Integer addNewSemaphore(Integer val1, List<Integer> val2) {
    this.elements.put(nextFreeAddress, new Pair<>(val1, val2));
    nextFreeAddress += 1;
    return nextFreeAddress - 1;
  }

  @Override
  public Map<Integer, Pair<Integer, List<Integer>>> getAll() {
    return this.elements;
  }

  @Override
  public synchronized boolean isDefined(Integer key) {
    return this.elements.containsKey(key);
  }

  @Override
  public synchronized void update(Integer key, Integer val1, List<Integer> val2)
      throws CustomException {
    if (!this.elements.containsKey(key))
      throw new CustomException("Key not found in semaphroe table");
    this.elements.put(key, new Pair<>(val1, val2));
  }

  @Override
  public synchronized Pair<Integer, List<Integer>> lookup(Integer key) throws CustomException {
    if (!this.elements.containsKey(key))
      throw new CustomException("Key not found in semaphore table");
    return this.elements.get(key);
  }
}
