package controller;

import Exceptions.CustomException;
import model.state.ProgramState;
import repository.Repository;
import repository.ToyRepository;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.List;

public interface Controller {

  void oneStepForAllPrg(List<ProgramState> prgList) throws IOException;

  void allStepsExecution() throws CustomException, IOException;

  Repository getRepository();
}
