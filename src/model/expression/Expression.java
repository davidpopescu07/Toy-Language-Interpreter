package model.expression;

import Exceptions.CustomException;
import model.state.Heap;
import model.state.MapSymbolTable;
import model.state.MyIDictionary;
import model.state.SymbolTable;
import model.type.IType;
import model.type.Type;
import model.value.Value;

import java.util.Map;

public interface Expression {
  Value evaluate(SymbolTable symTable, Heap<Integer, Value> heap) throws CustomException;

  IType typecheck(MyIDictionary<String, IType> typeEnv) throws CustomException;
}
