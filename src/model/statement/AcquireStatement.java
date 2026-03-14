package model.statement;

import Exceptions.CustomException;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntegerType;
import model.value.IntegerValue;

import java.io.InvalidClassException;

public class AcquireStatement implements Statement {

  private String variableName;

  public AcquireStatement(String v1) {
    this.variableName = v1;
  }

  @Override
  public ProgramState execute(ProgramState state) throws CustomException, InvalidClassException {
    synchronized (state.getSemaphoreTable()) {
      if (!state.getSymTable().isDefined(variableName))
        throw new CustomException("Variable " + variableName + " is not defined");
      if (!state.getSymTable().getType(variableName).equals(IntegerType.getInstance()))
        throw new CustomException("Variable " + variableName + " is not an integer");
      var foundIndex = state.getSymTable().getValue(variableName);
      IntegerValue ivalFoundIndex = (IntegerValue) foundIndex;
      if (!state.getSemaphoreTable().isDefined(ivalFoundIndex.getValue()))
        throw new CustomException("Index " + foundIndex + " is not defined in sempahoretable");
      else {
        var res = state.getSemaphoreTable().lookup(ivalFoundIndex.getValue());
        int NL = res.getValue().size();
        if (res.getKey() > NL) {
          if (!res.getValue().contains(state.getId())) {
            var newlis = res.getValue();
            newlis.add(state.getId());
            state.getSemaphoreTable().update(ivalFoundIndex.getValue(), res.getKey(), newlis);
          }
        } else state.getExecutionStack().push(new AcquireStatement(variableName));
      }
      return null;
    }
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    IType varType = typeEnv.lookup(variableName);
    if (!varType.equals(IntegerType.getInstance()))
      throw new CustomException("Variable type " + variableName + " is not integer");
    return typeEnv;
  }

  @Override
  public String toString() {
    return "acquire(" + variableName + ")";
  }
}
