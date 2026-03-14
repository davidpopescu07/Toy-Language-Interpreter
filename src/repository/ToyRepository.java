package repository;

import model.state.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToyRepository implements Repository {
  private List<ProgramState> programStates;
  private final String logFilePath;

  public ToyRepository(String logFilePath) {
    this.programStates = new ArrayList<>();
    this.logFilePath = logFilePath;
    clearLogFile();
  }

  private void clearLogFile() {
    try {
      PrintWriter writer = new PrintWriter(logFilePath);
      writer.close();
    } catch (IOException e) {
      System.out.println("Could not clear log file");
    }
  }

  @Override
  public void addProgramState(ProgramState state) {
    programStates.add(state);
  }

  @Override
  public List<ProgramState> getProgramStateList() {
    return programStates;
  }

  @Override
  public void setProgramStateList(List<ProgramState> arr) {
    programStates = arr;
  }

  @Override
  public void logProgramState(ProgramState state) throws IOException {
    try (PrintWriter printWriter =
        new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
      printWriter.println("---------- Program State ----------");
      printWriter.println(state.toString());
      printWriter.println("------------------------------------\n");
    }
  }
}
