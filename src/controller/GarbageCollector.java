package controller;

import model.value.RefValue;
import model.value.Value;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector {

  public static List<Integer> getAddrFromSymTable(Collection<Object> symTableValues) {
    return symTableValues.stream()
        .filter(v -> v instanceof RefValue)
        .map(v -> ((RefValue) v).getAddress())
        .collect(Collectors.toList());
  }

  public static List<Integer> getReachableHeapAddresses(
      List<Integer> rootAddresses, Map<Integer, Value> heap) {

    Set<Integer> visited = new HashSet<>(rootAddresses);
    Stack<Integer> worklist = new Stack<>();
    worklist.addAll(rootAddresses);

    while (!worklist.isEmpty()) {
      int addr = worklist.pop();

      Value v = heap.get(addr);

      if (v instanceof RefValue ref) {
        int nested = ref.getAddress();

        if (heap.containsKey(nested) && !visited.contains(nested)) {
          visited.add(nested);
          worklist.push(nested);
        }
      }
    }

    return new ArrayList<>(visited);
  }

  public static Map<Integer, Value> safeGarbageCollector(
      Collection<Object> symTableValues, Map<Integer, Value> heap) {

    List<Integer> symTableAddr = getAddrFromSymTable(symTableValues);
    List<Integer> reachable = getReachableHeapAddresses(symTableAddr, heap);

    return heap.entrySet().stream()
        .filter(e -> reachable.contains(e.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
