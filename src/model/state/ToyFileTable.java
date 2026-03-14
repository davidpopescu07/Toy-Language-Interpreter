package model.state;

import Exceptions.CustomException;
import Exceptions.FileNotOpenException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ToyFileTable implements FileTable {
  private Map<String, BufferedReader> fileTable = new HashMap<>();

  public Map<String, BufferedReader> getAll() {
    return fileTable;
  }

  @Override
  public boolean isOpen(String fileName) {
    return fileTable.containsKey(fileName);
  }

  @Override
  public void addOpenFile(String fileName, BufferedReader bufferReader) {
    fileTable.put(fileName, bufferReader);
  }

  @Override
  public BufferedReader getOpenFile(String fileName) {
    return fileTable.get(fileName);
  }

  @Override
  public void closeFile(String fileName) throws CustomException {
    try {
      fileTable.remove(fileName).close();
    } catch (IOException e) {
      throw new CustomException("Cannot close file");
    }
  }

  public String toString() {
    return fileTable.toString();
  }
}
