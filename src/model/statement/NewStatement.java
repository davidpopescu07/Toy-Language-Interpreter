package model.statement;

import Exceptions.CustomException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.state.Heap;
import model.type.IType;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class NewStatement implements Statement {

  private final String varName;
  private final Expression expression;

  public NewStatement(String varName, Expression expression) {
    this.varName = varName;
    this.expression = expression;
  }

  @Override
  public ProgramState execute(ProgramState state) throws CustomException {
    SymbolTable sym = state.getSymTable();
    Heap<Integer, Value> heap = state.getHeap();

    if (!sym.isDefined(varName)) {
      throw new CustomException("Variable " + varName + " is not defined");
    }

    Value varVal = sym.getValue(varName);

    if (!(varVal instanceof RefValue refVar)) {
      throw new CustomException("Variable " + varName + " is not a Ref type");
    }

    Value evaluated = expression.evaluate(sym, heap);

    if (!evaluated.getType().equals(refVar.getLocationType())) {
      throw new CustomException("Type mismatch in new(): expected " + refVar.getLocationType());
    }

    int address = heap.allocate(evaluated);

    sym.update(varName, new RefValue(address, refVar.getLocationType()));

    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    IType typevar = typeEnv.lookup(varName);
    IType typexp = expression.typecheck(typeEnv);
    if (typevar.equals(new RefType(typexp))) {
      return typeEnv;
    } else {
      throw new CustomException(
          "NEW stmt: right hand side and left hand side have different types");
    }
  }

  @Override
  public String toString() {
    return "new(" + varName + ", " + expression + ")";
  }
}
