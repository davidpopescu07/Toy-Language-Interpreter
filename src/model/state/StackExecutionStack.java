package model.state;

import java.util.LinkedList;
import java.util.List;

import Exceptions.StackEmptyException;
import model.statement.Statement;

public class StackExecutionStack implements ExecutionStack {

  private final List<Statement> stack = new LinkedList<>();

  @Override
  public void push(Statement statement) {
    stack.addFirst(statement);
  }

  @Override
  public Statement pop() throws StackEmptyException {
    if (isEmpty()) {
      throw new StackEmptyException("Stack is empty");
    }
    return stack.removeFirst();
  }

  @Override
  public boolean isEmpty() {
    return stack.isEmpty();
  }

  @Override
  public String toString() {
    if (stack.isEmpty()) return "[]";
    StringBuilder sb = new StringBuilder("[");
    for (Statement stmt : stack) {
      sb.append(stmt.toString()).append(", ");
    }
    sb.setLength(sb.length() - 2);
    sb.append("]");
    return sb.toString();
  }

  public Statement peek() throws StackEmptyException {
    if (isEmpty()) {
      throw new StackEmptyException("Stack is empty");
    }
    return stack.getFirst();
  }

  @Override
  public List<Statement> getContent() {
    return new LinkedList<>(stack);
  }
}
