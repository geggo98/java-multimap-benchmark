package de.schwetschke.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class SingletonList {
  private final static String input = "Dear Java developer: there is great MultiMap support in Java 8";

  @Benchmark
	public void positionsMergeHashMapHashSetAnonymousClass() {
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
        new HashSet<Integer>(){{add(currentPos);}},
        mergeSets);
    }
  }

  @Benchmark
	public void positionsMergeHashMapHashSetSingleton() {
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
	public void positionsMergeHashMapArrayListAnonymousClass() {
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
        new ArrayList<Integer>(){{add(currentPos);}},
        mergeLists);
    }
  }
  
  @Benchmark
	public void positionsMergeHashMapArrayListSingleton() {
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
}

