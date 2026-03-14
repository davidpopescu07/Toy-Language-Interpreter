package model.statement;

import Exceptions.CustomException;
import model.expression.Expression;
import model.state.MapSymbolTable;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntegerType;
import model.value.IntegerValue;
import model.value.Value;

import java.io.InvalidClassException;
import java.util.ArrayList;

public class CreateSemaphoreStatement implements Statement {

  private Expression exp1;
  private String variableName;

  public CreateSemaphoreStatement(Expression e1, String v1) {
    this.exp1 = e1;
    this.variableName = v1;
  }

  @Override
  public ProgramState execute(ProgramState state) throws CustomException, InvalidClassException {

    synchronized (state.getSemaphoreTable()) {
      MapSymbolTable symTable = state.getSymTable();
      Value num1 = exp1.evaluate(symTable, state.getHeap());
      if (!num1.getType().equals(IntegerType.getInstance()))
        throw new CustomException("Expression" + exp1.toString() + " is not an integer type");
      IntegerValue ivalNum1 = (IntegerValue) num1;
      var location =
          state.getSemaphoreTable().addNewSemaphore(ivalNum1.getValue(), new ArrayList<>());
      if (symTable.isDefined(variableName)
          && symTable.getType(variableName).equals(IntegerType.getInstance())) {
        state.getSymTable().update(variableName, new IntegerValue(location));
      } else throw new CustomException("Err");

      return null;
    }
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    IType varType = typeEnv.lookup(variableName);
    IType expType = exp1.typecheck(typeEnv);
    if (!(expType.equals(IntegerType.getInstance()) && varType.equals(IntegerType.getInstance())))
      throw new CustomException(
          "Type mismatch between expression " + exp1 + " and variable " + variableName);
    return typeEnv;
  }

  @Override
  public String toString() {
    return "createSemaphore(" + variableName + "," + exp1.toString() + ")";
  }
}
