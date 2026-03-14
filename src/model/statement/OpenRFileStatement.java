package model.statement;

import Exceptions.CustomException;
import Exceptions.FileAlreadyOpenException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.IType;
import model.type.StringType;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InvalidClassException;

public record OpenRFileStatement(Expression expression) implements Statement {

  @Override
  public ProgramState execute(ProgramState state) throws InvalidClassException, CustomException {
    var value = expression.evaluate(state.getSymTable(), state.getHeap());
    if (!(value instanceof StringValue(String fileName))) {
      throw new InvalidClassException("Type must be String");
    }

    if (state.getFileTable().isOpen(fileName)) {
      throw new FileAlreadyOpenException("File already open");
    }

    BufferedReader bufferReader;
    try {
      bufferReader = new BufferedReader(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      throw new CustomException("File not found");
    }
    state.getFileTable().addOpenFile(fileName, bufferReader);

    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    IType typexp = expression.typecheck(typeEnv);
    if (typexp.equals(StringType.getInstance())) {
      return typeEnv;
    } else {
      throw new CustomException("OpenRFile: expression is not a string");
    }
  }
}
