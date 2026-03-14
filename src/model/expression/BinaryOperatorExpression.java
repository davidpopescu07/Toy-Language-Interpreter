package model.expression;

import Exceptions.CustomException;
import Exceptions.DivisionWithZeroException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidTypeException;
import model.state.Heap;
import model.state.MyIDictionary;
import model.state.SymbolTable;
import model.type.BooleanType;
import model.type.IntegerType;
import model.type.IType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

import java.util.Map;

public record BinaryOperatorExpression(String operator, Expression left, Expression right)
    implements Expression {

  @Override
  public Value evaluate(SymbolTable symTable, Heap<Integer, Value> heap) throws CustomException {
    Value leftTerm = left.evaluate(symTable, heap);
    Value rightTerm = right.evaluate(symTable, heap);
    switch (operator) {
      case "+", "-", "*", "/":
        checkTypes(leftTerm, rightTerm, IntegerType.getInstance());
        var leftValue = (IntegerValue) leftTerm;
        var rightValue = (IntegerValue) rightTerm;
        return evaluateArithmeticExpression(leftValue, rightValue);
      case "&&", "||":
        checkTypes(leftTerm, rightTerm, BooleanType.getInstance());
        var leftValueB = (BooleanValue) leftTerm;
        var rightValueB = (BooleanValue) rightTerm;
        return evaluateBooleanExpression(leftValueB, rightValueB);
    }
    throw new InvalidOperationException("Unknown operator" + operator);
  }

  @Override
  public IType typecheck(MyIDictionary<String, IType> typeEnv) throws CustomException {
    IType typ1 = left.typecheck(typeEnv);
    IType typ2 = right.typecheck(typeEnv);

    switch (operator) {
      case "+", "-", "*", "/":
        if (typ1.equals(IntegerType.getInstance())) {
          if (typ2.equals(IntegerType.getInstance())) {
            return IntegerType.getInstance();
          } else {
            throw new CustomException("Arithmetic: second operand is not an integer");
          }
        } else {
          throw new CustomException("Arithmetic: first operand is not an integer");
        }
      case "&&", "||":
        if (typ1.equals(BooleanType.getInstance())) {
          if (typ2.equals(BooleanType.getInstance())) {
            return BooleanType.getInstance();
          } else {
            throw new CustomException("Logic: second operand is not a boolean");
          }
        } else {
          throw new CustomException("Logic: first operand is not a boolean");
        }
      default:
        throw new CustomException("Unknown operator: " + operator);
    }
  }

  private Value evaluateBooleanExpression(BooleanValue leftValue, BooleanValue rightValue)
      throws CustomException {
    return switch (operator) {
      case "&&" -> new BooleanValue(leftValue.value() && rightValue.value());
      case "||" -> new BooleanValue(leftValue.value() || rightValue.value());
      default -> throw new CustomException("Unreachable code");
    };
  }

  private void checkTypes(Value leftTerm, Value rightTerm, IType type) throws InvalidTypeException {
    if (!leftTerm.getType().equals(type) || !rightTerm.getType().equals(type)) {
      throw new InvalidTypeException("Invalid types for operator " + operator);
    }
  }

  private IntegerValue evaluateArithmeticExpression(IntegerValue leftValue, IntegerValue rightValue)
      throws CustomException {
    switch (operator) {
      case "+" -> {
        return new IntegerValue(leftValue.value() + rightValue.value());
      }
      case "-" -> {
        return new IntegerValue(leftValue.value() - rightValue.value());
      }
      case "*" -> {
        return new IntegerValue(leftValue.value() * rightValue.value());
      }
      case "/" -> {
        if (rightValue.value() == 0) {
          throw new DivisionWithZeroException("Division Overflow");
        }
        return new IntegerValue(leftValue.value() / rightValue.value());
      }
      default -> throw new CustomException("Unreachable code");
    }
  }

  @Override
  public String toString() {
    return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
  }
}
