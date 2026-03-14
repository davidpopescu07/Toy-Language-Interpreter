package model.state;

import Exceptions.KeyNotFoundException;
import model.type.IType;
import model.type.Type;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable implements SymbolTable {

  private final Map<String, Value> map = new HashMap<>();

  @Override
  public boolean isDefined(String variableName) {
    return map.containsKey(variableName);
  }

  @Override
  public IType getType(String variableName) throws KeyNotFoundException {

    if (!map.containsKey(variableName)) {
      throw new KeyNotFoundException("Key not found");
    }
    return map.get(variableName).getType();
  }

  @Override
  public void declareVariable(String variableName, IType type) {
    map.put(variableName, type.getDefaultValue());
  }

  @Override
  public SymbolTable deepCopy() {
    MapSymbolTable copy = new MapSymbolTable();
    copy.map.putAll(this.map);
    return copy;
  }

  @Override
  public void update(String variableName, Value value) throws KeyNotFoundException {

    if (!map.containsKey(variableName)) {
      throw new KeyNotFoundException("Key not found");
    }
    map.put(variableName, value);
  }

  @Override
  public Value getValue(String variableName) throws KeyNotFoundException {

    if (!map.containsKey(variableName)) {
      throw new KeyNotFoundException("Key not found");
    }
    return map.get(variableName);
  }

  @Override
  public Map<String, Value> getContent() {
    return new HashMap<>(map);
  }

  @Override
  public String toString() {
    if (map.isEmpty()) return "{}";
    StringBuilder sb = new StringBuilder("{");
    for (var entry : map.entrySet()) {
      sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append(", ");
    }
    sb.setLength(sb.length() - 2);
    sb.append("}");
    return sb.toString();
  }
}
