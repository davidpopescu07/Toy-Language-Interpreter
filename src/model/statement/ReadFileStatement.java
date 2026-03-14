package model.statement;

import Exceptions.CustomException;
import Exceptions.InvalidTypeException;
import Exceptions.KeyNotFoundException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntegerType;
import model.type.StringType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public record ReadFileStatement(Expression expression, String varName) implements Statement {
  @Override
  public ProgramState execute(ProgramState state) throws CustomException {
    if (!state.getSymTable().isDefined(varName)
        || !state.getSymTable().getType(varName).equals(IntegerType.getInstance())) {
      throw new InvalidTypeException(varName);
    }

    var value = expression.evaluate(state.getSymTable(), state.getHeap());
    if (!(value instanceof StringValue(String fileName))) {
      throw new InvalidTypeException("Type must be String");
    }
    if (!state.getFileTable().isOpen(fileName)) {
      throw new CustomException("File not found");
    }
    BufferedReader br = state.getFileTable().getOpenFile(fileName);
    String line;
    try {
      line = br.readLine();
    } catch (IOException e) {
      throw new CustomException("Error on reading");
    }
    if (line == null) {
      state.getSymTable().update(varName, new IntegerValue(0));
    } else {
      try {
        int valueInt = Integer.parseInt(line);
        state.getSymTable().update(varName, new IntegerValue(valueInt));
      } catch (NumberFormatException e) {
        throw new InvalidTypeException("Invalid integer in file: " + line);
      }
    }
    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    IType typexp = expression.typecheck(typeEnv);
    if (!typexp.equals(StringType.getInstance())) {
      throw new CustomException("ReadFile: expression is not a string");
    }
    IType typevar = typeEnv.lookup(varName);
    if (!typevar.equals(IntegerType.getInstance())) {
      throw new CustomException("ReadFile: variable is not an integer");
    }
    return typeEnv;
  }
}
