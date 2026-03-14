package model.state;

import Exceptions.CustomException;
import model.state.MyIDictionary;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
  private final Map<K, V> dictionary;

  public MyDictionary() {
    this.dictionary = new HashMap<>();
  }

  @Override
  public V lookup(K key) throws CustomException {
    if (!isDefined(key)) {
      throw new CustomException("Key not found in type environment: " + key);
    }
    return dictionary.get(key);
  }

  @Override
  public void add(K key, V value) {
    dictionary.put(key, value);
  }

  @Override
  public boolean isDefined(K key) {
    return dictionary.containsKey(key);
  }

  @Override
  public void update(K key, V value) throws CustomException {
    if (!isDefined(key)) {
      throw new CustomException("Key not found in type environment: " + key);
    }
    dictionary.put(key, value);
  }

  @Override
  public MyIDictionary<K, V> deepCopy() {
    MyDictionary<K, V> copy = new MyDictionary<>();
    for (Map.Entry<K, V> entry : dictionary.entrySet()) {
      copy.add(entry.getKey(), entry.getValue());
    }
    return copy;
  }

  @Override
  public Map<K, V> getContent() {
    return dictionary;
  }

  @Override
  public String toString() {
    return dictionary.toString();
  }
}
