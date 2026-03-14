package gui;

import controller.Controller;
import controller.ToyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.expression.BinaryOperatorExpression;
import model.expression.ConstantExpression;
import model.expression.ReadHeapExpression;
import model.expression.VariableExpression;
import model.state.*;
import model.statement.*;
import model.type.IType;
import model.type.IntegerType;
import model.type.RefType;
import model.type.StringType;
import model.value.IntegerValue;
import model.value.StringValue;
import repository.Repository;
import repository.ToyRepository;

public class Main extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
    Parent root = loader.load();
    MainController guiController = loader.getController();
    Repository repo = new ToyRepository("log.txt");
    ToyController controller = new ToyController(repo);

    ToyFileTable fileTable = new ToyFileTable();

    MyIDictionary<String, IType> typeEnv1 = new MyDictionary<>();

    ToySemaphoreTable semaphoreTable = new ToySemaphoreTable();

    Statement ex1 =
        new CompoundStatement(
            new VariableDeclarationStatement(IntegerType.getInstance(), "v"),
            new CompoundStatement(
                new AssignmentStatement(
                    new ConstantExpression(new model.value.IntegerValue(2)), "v"),
                new PrintStatement(new VariableExpression("v"))));
    ex1.typecheck(typeEnv1);
    ProgramState prg1 = new ProgramState(fileTable, ex1, semaphoreTable);
    repo.addProgramState(prg1);

    MyIDictionary<String, IType> typeEnv2 = new MyDictionary<>();

    Statement ex2 =
        new CompoundStatement(
            new VariableDeclarationStatement(new RefType(IntegerType.getInstance()), "a"),
            new CompoundStatement(
                new NewStatement("a", new ConstantExpression(new IntegerValue(10))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))));
    ex2.typecheck(typeEnv2);
    ProgramState prg2 = new ProgramState(fileTable, ex2, semaphoreTable);
    repo.addProgramState(prg2);
    MyIDictionary<String, IType> typeEnv3 = new MyDictionary<>();

    Statement ex3 =
        new CompoundStatement(
            new VariableDeclarationStatement(IntegerType.getInstance(), "v"),
            new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntegerType.getInstance()), "a"),
                new CompoundStatement(
                    new AssignmentStatement(new ConstantExpression(new IntegerValue(10)), "v"),
                    new CompoundStatement(
                        new NewStatement("a", new ConstantExpression(new IntegerValue(22))),
                        new CompoundStatement(
                            new ForkStatement(
                                new CompoundStatement(
                                    new WriteHeapStatement(
                                        new VariableExpression("a"),
                                        new ConstantExpression(new IntegerValue(30))),
                                    new CompoundStatement(
                                        new AssignmentStatement(
                                            new ConstantExpression(new IntegerValue(32)), "v"),
                                        new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new PrintStatement(
                                                new ReadHeapExpression(
                                                    new VariableExpression("a"))))))),
                            new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(
                                    new ReadHeapExpression(new VariableExpression("a")))))))));
    ex3.typecheck(typeEnv3);
    ProgramState prg3 = new ProgramState(fileTable, ex3, semaphoreTable);
    repo.addProgramState(prg3);

    MyIDictionary<String, IType> typeEnv4 = new MyDictionary<>();

    Statement declareVarF = new VariableDeclarationStatement(StringType.getInstance(), "varf");
    Statement declareVarC = new VariableDeclarationStatement(IntegerType.getInstance(), "varc");
    Statement assignVarF =
        new AssignmentStatement(new ConstantExpression(new StringValue("test.in")), "varf");
    Statement openFile = new OpenRFileStatement(new VariableExpression("varf"));
    Statement readVarC1 = new ReadFileStatement(new VariableExpression("varf"), "varc");
    Statement printVarC1 = new PrintStatement(new VariableExpression("varc"));
    Statement readVarC2 = new ReadFileStatement(new VariableExpression("varf"), "varc");
    Statement printVarC2 = new PrintStatement(new VariableExpression("varc"));
    Statement closeFile = new CloseRFileStatement(new VariableExpression("varf"));
    Statement ex4 =
        new CompoundStatement(
            declareVarF,
            new CompoundStatement(
                assignVarF,
                new CompoundStatement(
                    openFile,
                    new CompoundStatement(
                        declareVarC,
                        new CompoundStatement(
                            readVarC1,
                            new CompoundStatement(
                                printVarC1,
                                new CompoundStatement(
                                    readVarC2, new CompoundStatement(printVarC2, closeFile))))))));
    ex4.typecheck(typeEnv4);
    ProgramState prg4 = new ProgramState(fileTable, ex4, semaphoreTable);
    repo.addProgramState(prg4);

    MyIDictionary<String, IType> typeEnv5 = new MyDictionary<>();

    Statement ex5 =
        new CompoundStatement(
            new VariableDeclarationStatement(new RefType(IntegerType.getInstance()), "v1"),
            new CompoundStatement(
                new VariableDeclarationStatement(IntegerType.getInstance(), "cnt"),
                new CompoundStatement(
                    new NewStatement("v1", new ConstantExpression(new IntegerValue(1))),
                    new CompoundStatement(
                        new CreateSemaphoreStatement(
                            new ReadHeapExpression(new VariableExpression("v1")), "cnt"),
                        new CompoundStatement(
                            new ForkStatement(
                                new CompoundStatement(
                                    new AcquireStatement("cnt"),
                                    new CompoundStatement(
                                        new WriteHeapStatement(
                                            new VariableExpression("v1"),
                                            new BinaryOperatorExpression(
                                                "*",
                                                new ReadHeapExpression(
                                                    new VariableExpression("v1")),
                                                new ConstantExpression(new IntegerValue(10)))),
                                        new CompoundStatement(
                                            new PrintStatement(
                                                new ReadHeapExpression(
                                                    new VariableExpression("v1"))),
                                            new ReleaseStatement("cnt"))))),
                            new CompoundStatement(
                                new ForkStatement(
                                    new CompoundStatement(
                                        new AcquireStatement("cnt"),
                                        new CompoundStatement(
                                            new WriteHeapStatement(
                                                new VariableExpression("v1"),
                                                new BinaryOperatorExpression(
                                                    "*",
                                                    new ReadHeapExpression(
                                                        new VariableExpression("v1")),
                                                    new ConstantExpression(new IntegerValue(10)))),
                                            new CompoundStatement(
                                                new WriteHeapStatement(
                                                    new VariableExpression("v1"),
                                                    new BinaryOperatorExpression(
                                                        "*",
                                                        new ReadHeapExpression(
                                                            new VariableExpression("v1")),
                                                        new ConstantExpression(
                                                            new IntegerValue(2)))),
                                                new CompoundStatement(
                                                    new PrintStatement(
                                                        new ReadHeapExpression(
                                                            new VariableExpression("v1"))),
                                                    new ReleaseStatement("cnt")))))),
                                new CompoundStatement(
                                    new AcquireStatement("cnt"),
                                    new CompoundStatement(
                                        new PrintStatement(
                                            new BinaryOperatorExpression(
                                                "-",
                                                new ReadHeapExpression(
                                                    new VariableExpression("v1")),
                                                new ConstantExpression(new IntegerValue(1)))),
                                        new ReleaseStatement("cnt")))))))));
    ex5.typecheck(typeEnv5);
    ProgramState prg5 = new ProgramState(fileTable, ex5, semaphoreTable);
    repo.addProgramState(prg5);

    guiController.setController(controller);
    stage.setScene(new Scene(root, 1000, 700));
    stage.setTitle("Toy Language Interpreter");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
