package view;

import java.io.IO;

public class ExitCommand extends Command {
  public ExitCommand(String key, String description) {
    super(key, description);
  }

  @Override
  public void execute() {
    IO.println("Exiting program...");
    System.exit(0);
  }
}
