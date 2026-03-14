package view;

import java.io.IO;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
  private final Map<String, Command> commands;

  public TextMenu() {
    commands = new HashMap<>();
  }

  public void addCommand(Command c) {
    commands.put(c.getKey(), c);
  }

  private void printMenu() {
    IO.println("\n Menu");
    for (Command com : commands.values()) {
      System.out.printf("%4s : %s%n", com.getKey(), com.getDescription());
    }
  }

  public void show() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      printMenu();
      IO.print("Input the option: ");
      String key = scanner.nextLine();
      Command com = commands.get(key);
      if (com == null) {
        IO.println("Invalid option!");
      } else {
        com.execute();
      }
    }
  }
}
