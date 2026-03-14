package model.expression;

import Exceptions.CustomException;
import model.state.Heap;
import model.state.MapSymbolTable;

import model.state.MyIDictionary;
import model.state.SymbolTable;
import model.type.BooleanType;
import model.type.IType;
import model.type.IntegerType;
import model.type.Type;

import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

import java.util.Map;

public class RelationalExpression implements Expression {
  private final Expression exp1;
  private final Expression exp2;
  private final String operator;

  public RelationalExpression(String operator, Expression exp1, Expression exp2) {
    this.exp1 = exp1;
    this.exp2 = exp2;
    this.operator = operator;
  }

  @Override
  public Value evaluate(SymbolTable symTable, Heap<Integer, Value> heap) throws CustomException {
    Value v1 = exp1.evaluate(symTable, heap);
    Value v2 = exp2.evaluate(symTable, heap);

    if (!(v1.getType().equals(IntegerType.getInstance())))
      throw new CustomException("First operand is not an integer.");
    if (!(v2.getType().equals(IntegerType.getInstance())))
      throw new CustomException("Second operand is not an integer.");

    int n1 = ((IntegerValue) v1).getValue();
    int n2 = ((IntegerValue) v2).getValue();

    return switch (operator) {
      case "<" -> new BooleanValue(n1 < n2);
      case "<=" -> new BooleanValue(n1 <= n2);
      case "==" -> new BooleanValue(n1 == n2);
      case "!=" -> new BooleanValue(n1 != n2);
      case ">" -> new BooleanValue(n1 > n2);
      case ">=" -> new BooleanValue(n1 >= n2);
      default -> throw new CustomException("Invalid relational operator: " + operator);
    };
  }

  @Override
  public IType typecheck(MyIDictionary<String, IType> typeEnv) throws CustomException {
    IType typ1 = exp1.typecheck(typeEnv);
    IType typ2 = exp2.typecheck(typeEnv);

    if (typ1.equals(IntegerType.getInstance())) {
      if (typ2.equals(IntegerType.getInstance())) {
        return BooleanType.getInstance();
      } else {
        throw new CustomException("Relational: second operand is not an integer");
      }
    } else {
      throw new CustomException("Relational: first operand is not an integer");
    }
  }

  @Override
  public String toString() {
    return exp1.toString() + " " + operator + " " + exp2.toString();
  }
}
