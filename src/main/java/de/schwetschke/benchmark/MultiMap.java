package de.schwetschke.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class MultiMap {
  private final static String input = "Dear Java developer: there is great MultiMap support in Java 8";

  @Benchmark
  public void wellHelloThere() {
      // this method was intentionally left blank.
  }

  @Benchmark
  public void frequencyComputeTreeMap() {
    // We want to know how often the input string contains each character
    // We use a TreeMap, so the output will be sorted nicely
    final Map<Character, Integer> frequencyOfChar = new TreeMap<>();
    for (char currentChar : input.toCharArray()) {
      // Initialize the frequency list of neccessary
      frequencyOfChar.putIfAbsent(currentChar, 0);
      // Increase the frequency of the current character
      frequencyOfChar.compute(currentChar, (key, oldValue) -> oldValue + 1 );
    } 
  }
 
  @Benchmark
  public void positionsComputeTreeMapTreeSet() {
    // We want a set of positions of each character in the input string
    // We use a TreeMap, so the output will be sorted nicely
    final Map<Character, Set<Integer>> positionsOfChar = new TreeMap<>();
    for (int z = 0; z < input.length(); z++) {
      // Get the current character
      final char currentChar = input.charAt(z);
      positionsOfChar
        // Add an empty set for this character if neccessary
        // We use a TreeSet, so the output will be sorted nicely
        .computeIfAbsent(currentChar, y -> new TreeSet<>())
        // Add the current position to the set of positions for this character
        .add(z);
    }
  }

  @Benchmark
  public void frequencyMergeTreeMap() {
    // We want to know how often the input string contains each character
    // We use a tree map, so the output will be sorted nicely
    final Map<Character, Integer> frequencyOfChar = new TreeMap<>();
    for (char currentChar : input.toCharArray()) {
      frequencyOfChar.merge(currentChar, 1, Integer::sum);
    }

  }

  @Benchmark
	public void positionsMergeTreeMapTreeSet() {
      // We want a set of positions of each character in the input string
    // We use a tree map, so the output will be sorted nicely
    final Map<Character, Set<Integer>> positionsOfChar = new TreeMap<>();
    final BiFunction<Set<Integer>, Set<Integer>, Set<Integer>> mergeSets = (z,y) -> {
      final Set<Integer> x = new TreeSet<>(z);
      x.addAll(y);
      return x; 
    };

    for (int z = 0; z < input.length(); z++) {
      // We need the value in a final variable, so we can access it from a lambda
      final int currentPos = z;
      // Get the current character
      final char currentChar = input.charAt(currentPos);
      positionsOfChar.merge(
        currentChar, 
        Collections.singleton(currentPos),
        mergeSets);
    }
  }

  @Benchmark
  public void frequencyStreamTreeMap() {
    // We want to know how often the input string contains each character
    // We use a tree map, so the output will be sorted nicely
    Map<Character, Long> frequencyOfChar = input.chars()
      .mapToObj(z -> (char) z)
      .collect(Collectors.groupingBy(
        Function.identity(), TreeMap::new, Collectors.counting()));
  }

  @Benchmark
  public void postionsStreamTreeMapTreeSet() {
    // We want a set of positions of each character in the input string
    // We use a tree map, so the output will be sorted nicely
    Map<Character, Set<Integer>> positionsOfChar = 
    IntStream.range(0, input.length())
      .boxed()
      .collect(Collectors.groupingBy(
        z -> input.charAt(z),
        TreeMap::new,
        Collectors.toCollection(TreeSet::new)));
  
  }

  @Benchmark
  public void frequencyComputeHashMap() {
    // We want to know how often the input string contains each character
    // We use a TreeMap, so the output will be sorted nicely
    final Map<Character, Integer> frequencyOfChar = new HashMap<>();
    for (char currentChar : input.toCharArray()) {
      // Initialize the frequency list of neccessary
      frequencyOfChar.putIfAbsent(currentChar, 0);
      // Increase the frequency of the current character
      frequencyOfChar.compute(currentChar, (key, oldValue) -> oldValue + 1 );
    } 
  }
 
  @Benchmark
  public void positionsComputeHashMapHashSet() {
    // We want a set of positions of each character in the input string
    // We use a TreeMap, so the output will be sorted nicely
    final Map<Character, Set<Integer>> positionsOfChar = new HashMap<>();
    for (int z = 0; z < input.length(); z++) {
      // Get the current character
      final char currentChar = input.charAt(z);
      positionsOfChar
        // Add an empty set for this character if neccessary
        // We use a TreeSet, so the output will be sorted nicely
        .computeIfAbsent(currentChar, y -> new HashSet<>())
        // Add the current position to the set of positions for this character
        .add(z);
    }
  }

  @Benchmark
  public void frequencyMergeHashMap() {
    // We want to know how often the input string contains each character
    // We use a tree map, so the output will be sorted nicely
    final Map<Character, Integer> frequencyOfChar = new HashMap<>();
    for (char currentChar : input.toCharArray()) {
      frequencyOfChar.merge(currentChar, 1, Integer::sum);
    }

  }

  @Benchmark
	public void positionsMergeHashMapHashSet() {
      // We want a set of positions of each character in the input string
    // We use a tree map, so the output will be sorted nicely
    final Map<Character, Set<Integer>> positionsOfChar = new HashMap<>();
    final BiFunction<Set<Integer>, Set<Integer>, Set<Integer>> mergeSets = (z,y) -> {
      final Set<Integer> x = new HashSet<>(z);
      x.addAll(y);
      return x; 
    };

    for (int z = 0; z < input.length(); z++) {
      // We need the value in a final variable, so we can access it from a lambda
      final int currentPos = z;
      // Get the current character
      final char currentChar = input.charAt(currentPos);
      positionsOfChar.merge(
        currentChar, 
        Collections.singleton(currentPos),
        mergeSets);
    }
  }

  @Benchmark
  public void frequencyStreamHashMap() {
    // We want to know how often the input string contains each character
    // We use a tree map, so the output will be sorted nicely
    Map<Character, Long> frequencyOfChar = input.chars()
      .mapToObj(z -> (char) z)
      .collect(Collectors.groupingBy(
        Function.identity(), Collectors.counting()));
  }

  @Benchmark
  public void postionsStreamHashMapHashSet() {
    // We want a set of positions of each character in the input string
    // We use a tree map, so the output will be sorted nicely
    Map<Character, Set<Integer>> positionsOfChar = 
    IntStream.range(0, input.length())
      .boxed()
      .collect(Collectors.groupingBy(
        z -> input.charAt(z),
        Collectors.toSet()));
  }

  @Benchmark
  public void positionsComputeHashMapArrayList() {
    // We want a set of positions of each character in the input string
    // We use a TreeMap, so the output will be sorted nicely
    final Map<Character, List<Integer>> positionsOfChar = new HashMap<>();
    for (int z = 0; z < input.length(); z++) {
      // Get the current character
      final char currentChar = input.charAt(z);
      positionsOfChar
        // Add an empty set for this character if neccessary
        // We use a TreeSet, so the output will be sorted nicely
        .computeIfAbsent(currentChar, y -> new ArrayList<>())
        // Add the current position to the set of positions for this character
        .add(z);
    }
  }

  @Benchmark
  public void positionsComputeHashMapLinkedList() {
    // We want a set of positions of each character in the input string
    // We use a TreeMap, so the output will be sorted nicely
    final Map<Character, List<Integer>> positionsOfChar = new HashMap<>();
    for (int z = 0; z < input.length(); z++) {
      // Get the current character
      final char currentChar = input.charAt(z);
      positionsOfChar
        // Add an empty set for this character if neccessary
        // We use a TreeSet, so the output will be sorted nicely
        .computeIfAbsent(currentChar, y -> new LinkedList<>())
        // Add the current position to the set of positions for this character
        .add(z);
    }
  }

  @Benchmark
	public void positionsMergeHashMapArrayList() {
      // We want a set of positions of each character in the input string
    // We use a tree map, so the output will be sorted nicely
    final Map<Character, List<Integer>> positionsOfChar = new HashMap<>();
    final BiFunction<List<Integer>, List<Integer>, List<Integer>> mergeLists = (z,y) -> {
      final List<Integer> x = new ArrayList<>(z);
      x.addAll(y);
      return x; 
    };

    for (int z = 0; z < input.length(); z++) {
      // We need the value in a final variable, so we can access it from a lambda
      final int currentPos = z;
      // Get the current character
      final char currentChar = input.charAt(currentPos);
      positionsOfChar.merge(
        currentChar, 
        Collections.singletonList(currentPos),
        mergeLists);
    }
  }

  @Benchmark
	public void positionsMergeHashMapLinkedList() {
    // We want a set of positions of each character in the input string
    // We use a tree map, so the output will be sorted nicely
    final Map<Character, List<Integer>> positionsOfChar = new HashMap<>();
    final BiFunction<List<Integer>, List<Integer>, List<Integer>> mergeLists = (z,y) -> {
      final List<Integer> x = new LinkedList<>(z);
      x.addAll(y);
      return x; 
    };

    for (int z = 0; z < input.length(); z++) {
      // We need the value in a final variable, so we can access it from a lambda
      final int currentPos = z;
      // Get the current character
      final char currentChar = input.charAt(currentPos);
      positionsOfChar.merge(
        currentChar, 
        Collections.singletonList(currentPos),
        mergeLists);
    }
  }

  @Benchmark
  public void postionsStreamHashMapArrayList() {
    // We want a set of positions of each character in the input string
    // We use a tree map, so the output will be sorted nicely
    Map<Character, List<Integer>> positionsOfChar = 
    IntStream.range(0, input.length())
      .boxed()
      .collect(Collectors.groupingBy(
        z -> input.charAt(z),
        Collectors.toList()));
  }

  public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MultiMap.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}

