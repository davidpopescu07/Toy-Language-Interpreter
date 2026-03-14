package controller;

import Exceptions.CustomException;
import model.state.ProgramState;
import repository.Repository;

import java.io.IO;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ToyController implements Controller {
  private final Repository repository;
  private boolean displayFlag = true;
  private ExecutorService executor;

  public ToyController(Repository repository) {
    this.repository = repository;
    this.executor = Executors.newFixedThreadPool(2); // or any number of threads you need
  }

  public void setDisplayFlag(boolean displayFlag) {
    this.displayFlag = displayFlag;
  }

  public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
    return inProgramList.stream().filter(p -> p.isNotComplete()).collect(Collectors.toList());
  }

  @Override
  public void oneStepForAllPrg(List<ProgramState> prgList) throws IOException {
    prgList.forEach(
        prg -> {
          try {
            repository.logProgramState(prg);
          } catch (IOException | CustomException e) {
            throw new RuntimeException(e);
          }
        });

    List<Callable<ProgramState>> callList =
        prgList.stream()
            .map(
                (ProgramState p) ->
                    (Callable<ProgramState>)
                        (() -> {
                          try {
                            return p.oneStep();
                          } catch (CustomException | InvalidClassException e) {
                            throw new RuntimeException(e);
                          }
                        }))
            .collect(Collectors.toList());

    List<ProgramState> newPrgList = new ArrayList<>();
    try {
      executor.invokeAll(callList).stream()
          .map(
              future -> {
                try {
                  return future.get();
                } catch (Exception e) {
                  return null;
                }
              })
          .filter(p -> p != null)
          .forEach(newPrgList::add);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }

    for (ProgramState newPrg : newPrgList) {
      if (!prgList.contains(newPrg)) {
        prgList.add(newPrg);
      }
    }

    prgList.forEach(
        prg -> {
          try {
            repository.logProgramState(prg);
          } catch (IOException e) {
            throw new RuntimeException(e);
          } catch (CustomException e) {
            throw new RuntimeException(e);
          }
        });

    repository.setProgramStateList(prgList);
  }

  public void allStepsExecution() throws CustomException, IOException {
    executor = Executors.newFixedThreadPool(2);

    List<ProgramState> prgList = removeCompletedPrograms(repository.getProgramStateList());

    while (prgList.size() > 0) {
      oneStepForAllPrg(prgList);

      Collection<Object> allSymTableValues = new ArrayList<>();
      for (ProgramState prg : prgList) {
        allSymTableValues.addAll(prg.getSymTable().getContent().values());
      }

      prgList
          .getFirst()
          .getHeap()
          .setContent(
              GarbageCollector.safeGarbageCollector(
                  allSymTableValues, prgList.getFirst().getHeap().getAll()));

      prgList = removeCompletedPrograms(repository.getProgramStateList());
    }

    executor.shutdownNow();

    repository.setProgramStateList(prgList);

    IO.println("Program execution finished.");
  }

  @Override
  public Repository getRepository() {
    return repository;
  }
}
