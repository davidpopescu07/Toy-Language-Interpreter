# Toy Language Interpreter

A multi-threaded interpreter for a simple imperative programming language, built in Java with a JavaFX GUI. This project demonstrates core concepts of language interpretation including heap management, file I/O, concurrent execution with fork semantics, and counting semaphores for synchronization.

---

## Features

- **Step-by-step execution** - Execute programs one statement at a time or run to completion
- **Multi-threaded execution** - Fork statements create new threads with independent execution stacks
- **Heap memory management** - Dynamic memory allocation with automatic garbage collection
- **Counting semaphores** - Thread synchronization primitives (acquire/release)
- **File I/O** - Read from external files
- **Type checking** - Static type checking before execution
- **GUI debugger** - Visual inspection of program state, symbol tables, heap, and semaphore tables


## GUI Usage

1. **Select a program** from the list
2. **Click "Run One Step"** to execute one statement across all threads
3. **Inspect state** in the tables:
   - Execution Stack: current statements to execute
   - Symbol Table: variable bindings for selected thread
   - Heap: all heap-allocated values
   - File Table: open file handles
   - Semaphore Table: semaphore states (permits, waiting threads)
   - Output: printed values
4. **Select different threads** in the Program States list to view their individual state

---

## Architecture

The interpreter follows the **MVC pattern**:

- **Model**: `ProgramState`, statements, expressions, values, types
- **View**: JavaFX GUI (`MainController`, `MainView.fxml`)
- **Controller**: `ToyController` manages execution with a thread pool

**Execution Model**:
1. Each `ProgramState` has its own execution stack and symbol table
2. Heap, file table, output, and semaphore table are shared across all threads
3. `oneStepForAllPrg()` executes one step for each active thread in parallel
4. Garbage collection removes unreachable heap entries after each step

---

