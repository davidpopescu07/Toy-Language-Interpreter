package view;

import controller.Controller;
import model.state.ProgramState;

import java.io.IO;

public class RunExample extends Command {
  private final Controller controller;
  private final ProgramState program;

  public RunExample(String key, String description, Controller controller, ProgramState program) {
    super(key, description);
    this.controller = controller;
    this.program = program;
  }

  @Override
  public void execute() {
    try {
      controller.getRepository().getProgramStateList().clear();
      controller.getRepository().addProgramState(program);

      controller.allStepsExecution();

    } catch (Exception e) {
      IO.println("Error during execution: " + e.getMessage());
    }
  }
}
