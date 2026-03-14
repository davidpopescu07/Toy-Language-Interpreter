package model.state;

import Exceptions.CustomException;

import java.io.BufferedReader;

public interface FileTable {
  boolean isOpen(String fileName);

  void addOpenFile(String fileName, BufferedReader bufferReader);

  BufferedReader getOpenFile(String fileName);

  void closeFile(String fileName) throws CustomException;
}
