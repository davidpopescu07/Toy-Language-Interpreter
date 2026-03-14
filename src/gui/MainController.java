package gui;

import controller.ToyController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.state.ProgramState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController {

  private ToyController controller;

  private List<ProgramState> currentlyExecutingPrograms;
  private List<ProgramState> allAvailablePrograms;

  @FXML private ListView<Integer> programsListView; // Program IDs

  @FXML private ListView<String> outputListView;

  @FXML private TextField programStateIdField;

  @FXML private ListView<Integer> currentProgramsListView;

  @FXML private ListView<String> executionStackListView;

  @FXML private TableView<Map.Entry<String, model.value.Value>> symTableId;

  @FXML private TableColumn<Map.Entry<String, model.value.Value>, String> symVarColumn;

  @FXML private TableColumn<Map.Entry<String, model.value.Value>, String> symValueColumn;

  @FXML private TableView<Map.Entry<Integer, model.value.Value>> heapId;

  @FXML private TableColumn<Map.Entry<Integer, model.value.Value>, String> heapAddressColumn;

  @FXML private TableColumn<Map.Entry<Integer, model.value.Value>, String> heapValueColumn;

  @FXML private ListView<String> fileTableListView;

  @FXML private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreId;

  @FXML
  private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer>
      semaphoreTableIndexColumn;

  @FXML
  private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer>
      semaphoreTableValueColumn;

  @FXML
  private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, List<Integer>>
      semaphoreTableListColumn;

  @FXML private Button runOneStepButton;

  @FXML
  private void initialize() {
    currentlyExecutingPrograms = new ArrayList<>();

    symVarColumn.setCellValueFactory(
        data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getKey()));
    symValueColumn.setCellValueFactory(
        data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().toString()));

    heapAddressColumn.setCellValueFactory(
        data ->
            new javafx.beans.property.SimpleStringProperty(
                String.valueOf(data.getValue().getKey())));
    heapValueColumn.setCellValueFactory(
        data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().toString()));

    semaphoreTableIndexColumn.setCellValueFactory(
        data -> new SimpleObjectProperty<>(data.getValue().getKey()));

    semaphoreTableValueColumn.setCellValueFactory(
        data -> new SimpleObjectProperty<>(data.getValue().getValue().getKey()));

    semaphoreTableListColumn.setCellValueFactory(
        data -> new SimpleObjectProperty<>(data.getValue().getValue().getValue()));

    runOneStepButton.setOnAction(e -> onRunOneStep());

    programsListView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (newVal != null) {
                selectProgramToExecute(newVal);
              }
              refreshAll();
            });

    currentProgramsListView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((obs, oldVal, newVal) -> refreshAll());
  }

  public void setController(ToyController controller) {
    this.controller = controller;
    allAvailablePrograms = new ArrayList<>(controller.getRepository().getProgramStateList());
    populateProgramList();
  }

  private void populateProgramList() {
    if (controller == null) return;
    List<Integer> ids =
        allAvailablePrograms.stream().map(ProgramState::getId).collect(Collectors.toList());
    programsListView.setItems(FXCollections.observableArrayList(ids));
  }

  private void selectProgramToExecute(Integer programId) {
    ProgramState selectedProgram =
        allAvailablePrograms.stream().filter(p -> p.getId() == programId).findFirst().orElse(null);

    if (selectedProgram != null) {
      currentlyExecutingPrograms.clear();
      currentlyExecutingPrograms.add(selectedProgram);
      updateCurrentProgramsList();
    }
  }

  private void updateCurrentProgramsList() {
    List<Integer> currentIds =
        currentlyExecutingPrograms.stream().map(ProgramState::getId).collect(Collectors.toList());
    currentProgramsListView.setItems(FXCollections.observableArrayList(currentIds));
  }

  private ProgramState getSelectedProgram() {
    Integer id = currentProgramsListView.getSelectionModel().getSelectedItem();
    if (id == null) {
      if (!currentlyExecutingPrograms.isEmpty()) {
        return currentlyExecutingPrograms.get(0);
      }
      return null;
    }
    return currentlyExecutingPrograms.stream()
        .filter(p -> p.getId() == id)
        .findFirst()
        .orElse(null);
  }

  private void onRunOneStep() {
    try {
      if (controller == null || currentlyExecutingPrograms.isEmpty()) {
        showError("Please select a program from 'All Program IDs' first.");
        return;
      }

      controller.oneStepForAllPrg(currentlyExecutingPrograms);

      updateCurrentProgramsList();

      refreshAll();

      currentlyExecutingPrograms =
          currentlyExecutingPrograms.stream()
              .filter(ProgramState::isNotComplete)
              .collect(Collectors.toList());

      updateCurrentProgramsList();

      refreshAll();

    } catch (Exception e) {
      showError(e.getMessage());
    }
  }

  private void refreshAll() {
    ProgramState prg = getSelectedProgram();
    if (prg == null) return;

    programStateIdField.setText(String.valueOf(prg.getId()));

    executionStackListView.setItems(
        FXCollections.observableArrayList(
            prg.getExecutionStack().getContent().stream()
                .map(Object::toString)
                .collect(Collectors.toList())));

    symTableId.setItems(
        FXCollections.observableArrayList(prg.getSymTable().getContent().entrySet()));

    heapId.setItems(FXCollections.observableArrayList(prg.getHeap().getAll().entrySet()));

    outputListView.setItems(
        FXCollections.observableArrayList(
            prg.getOut().getList().stream().map(Object::toString).collect(Collectors.toList())));

    semaphoreId.setItems(
        FXCollections.observableArrayList(prg.getSemaphoreTable().getAll().entrySet()));

    semaphoreId.refresh();

    fileTableListView.setItems(
        FXCollections.observableArrayList(prg.getFileTable().getAll().keySet()));
  }

  private void showError(String msg) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText("Execution error");
    alert.setContentText(msg);
    alert.showAndWait();
  }
}
