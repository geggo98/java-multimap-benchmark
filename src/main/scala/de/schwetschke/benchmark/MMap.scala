package de.schwetschke.benchmark

import org.openjdk.jmh.annotations.Benchmark
import collection.mutable 

class ScalaMultiMap {
  import ScalaMultiMap.input

  @Benchmark
  def immuatebleFrequency() {
    input.groupBy(identity).mapValues(_.length)
  }

  @Benchmark
  def mutableFrequency() {
    val frequency = mutable.Map.empty[Char, Int].withDefaultValue(0);
    input.foreach(z => frequency(z) = (frequency(z)+1))
    frequency.toMap
  }

  @Benchmark
  def immutablePositions() {
    input.zipWithIndex.groupBy(_._1).mapValues(_.map(_._2))
  }

  @Benchmark
  def mutablePositionsTupleAccess() {
    val positions = mutable.Map.empty[Char, mutable.ArrayBuffer[Int]].withDefault(_ => mutable.ArrayBuffer.empty[Int])
    input.zipWithIndex.foreach(z => positions(z._1) = (positions(z._1) += z._2))
  }

  @Benchmark
  def mutablePositionsPatternMatching() {
    val positions = mutable.Map.empty[Char, mutable.ArrayBuffer[Int]].withDefault(_ => mutable.ArrayBuffer.empty[Int])
    input.zipWithIndex.foreach{case (char, pos) => positions(char) = (positions(char) += pos)}
  }

}

object ScalaMultiMap {
  val input : String = "Dear Java developer: there is great MultiMap support in Java 8";
}
