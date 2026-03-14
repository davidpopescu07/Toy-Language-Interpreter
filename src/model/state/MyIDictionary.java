package model.state;

import Exceptions.CustomException;

import java.util.Map;

public interface MyIDictionary<K, V> {
  V lookup(K key) throws CustomException;

  void add(K key, V value);

  boolean isDefined(K key);

  void update(K key, V value) throws CustomException;

  Map<K, V> getContent();

  String toString();

  MyIDictionary<K, V> deepCopy();
}
