package model.state;

import Exceptions.CustomException;
import model.statement.Statement;
import model.value.Value;

import java.io.InvalidClassException;

public class ProgramState {
  private static int lastId = 0;
  private int id;
  private final ExecutionStack executionStack;
  private final MapSymbolTable symTable;
  private final Out out;
  private final Statement originalProgram;
  private final ToyHeap<Value> heap;
  private final ToySemaphoreTable semaphoreTable;
  public final ToyFileTable fileTable;

  public ProgramState(ToyFileTable fileTable, Statement program, ToySemaphoreTable semaphoreTable) {

    this.fileTable = fileTable;
    this.semaphoreTable = new ToySemaphoreTable();
    this.heap = new ToyHeap<>();
    this.executionStack = new StackExecutionStack();
    this.symTable = new MapSymbolTable();
    this.out = new ListOut();
    this.originalProgram = program;
    this.executionStack.push(program);

    this.id = setId();
  }

  public ProgramState(
      ExecutionStack exeStack,
      MapSymbolTable symTable,
      Out out,
      ToyFileTable fileTable,
      ToyHeap<Value> heap,
      ToySemaphoreTable semaphoreTable) {
    this.executionStack = exeStack;
    this.symTable = symTable;
    this.out = out;
    this.fileTable = fileTable;
    this.heap = heap;
    this.semaphoreTable = semaphoreTable;
    this.originalProgram = null;
    this.id = setId();
  }

  public synchronized int setId() {
    lastId++;
    return lastId;
  }

  public int getId() {
    return this.id;
  }

  public Boolean isNotComplete() {
    return !this.executionStack.isEmpty();
  }

  public ProgramState oneStep() throws CustomException, InvalidClassException {
    if (this.executionStack.isEmpty()) throw new CustomException("Execution stack is empty");
    else {
      var currentStatement = this.executionStack.pop();
      return currentStatement.execute(this);
    }
  }

  public ExecutionStack getExecutionStack() {
    return executionStack;
  }

  public void resetID() {
    this.id = 0;
  }

  public MapSymbolTable getSymTable() {
    return symTable;
  }

  public Out getOut() {
    return out;
  }

  public ToyFileTable getFileTable() {
    return fileTable;
  }

  public ToyHeap<Value> getHeap() {
    return heap;
  }

  public ToySemaphoreTable getSemaphoreTable() {
    return semaphoreTable;
  }

  //  public Statement getOriginalProgram() {
  //    return originalProgram;
  //  }

  @Override
  public String toString() {
    String current;
    try {
      if (executionStack.isEmpty()) {
        current = "None (stack empty)";
      } else {
        current = executionStack.peek().toString();
      }
    } catch (Exception e) {
      current = "Error getting current instruction: " + e.getMessage();
    }
    return "Program ID: "
        + id
        + "\n"
        + "Execution Stack: "
        + executionStack
        + "\n"
        + "Symbol Table: "
        + symTable
        + "\n"
        + "Heap: "
        + heap
        + "\n"
        + "Current: "
        + current
        + "\n"
        + "Output: "
        + out
        + "\n"
        + "File Table :"
        + fileTable.toString()
        + "\n";
  }
}
