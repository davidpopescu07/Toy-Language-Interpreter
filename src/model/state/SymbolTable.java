package model.state;

import Exceptions.KeyNotFoundException;
import model.type.IType;
import model.type.Type;
import model.value.Value;

import java.util.Map;

public interface SymbolTable {
  boolean isDefined(String variableName);

  IType getType(String variableName) throws KeyNotFoundException;

  void declareVariable(String variableName, IType type);

  void update(String variableName, Value value) throws KeyNotFoundException;

  Value getValue(String variableName) throws KeyNotFoundException;

  Map<String, Value> getContent();

  SymbolTable deepCopy();

  String toString();
}
