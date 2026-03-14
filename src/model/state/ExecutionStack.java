package model.state;

import Exceptions.StackEmptyException;
import model.statement.Statement;

import java.util.EmptyStackException;
import java.util.List;

public interface ExecutionStack {
  void push(Statement statement);

  Statement pop() throws EmptyStackException, StackEmptyException;

  boolean isEmpty();

  String toString();

  Statement peek() throws StackEmptyException;

  List<Statement> getContent();
}
