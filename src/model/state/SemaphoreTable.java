package model.state;

import Exceptions.CustomException;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface SemaphoreTable<K, V1, V2> {

  Integer addNewSemaphore(V1 val1, V2 val2);

  Map<Integer, Pair<Integer, List<Integer>>> getAll();

  void update(Integer key, Integer val1, List<Integer> val2) throws CustomException;

  Pair<Integer, List<Integer>> lookup(K key) throws CustomException;

  void update(K key, V1 val1, V2 val2) throws CustomException;

  boolean isDefined(K key);
}
