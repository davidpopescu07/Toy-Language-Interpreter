package repository;

import Exceptions.CustomException;
import model.state.ProgramState;
import java.io.IOException;
import java.util.List;

public interface Repository {
  void addProgramState(ProgramState state);

  List<ProgramState> getProgramStateList();

  void setProgramStateList(List<ProgramState> arr);

  void logProgramState(ProgramState state) throws CustomException, IOException;
}
