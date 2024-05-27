package org.mwatt.problems

class IsFunction {
  private def readNLines(n: Int): Iterator[String] = {
    (1 to n).map(_ => scala.io.StdIn.readLine).iterator
  }

  def main(args: Array[String]) = {
    val numTestCases = readNLines(1).map(_.toInt).toList.head

    for (_ <- 1 to numTestCases) {
      val numPairs = readNLines(1).map(_.toInt).toList.head
      val lines = readNLines(numPairs).toList
      val pairs = lines.map(_.split(" ")).map(l => l(0) -> l(1))
      val map = pairs.toMap
      val isFunction = map.size == map.values.toSet.size
      println(if (isFunction) "YES" else "NO")
    }
  }

}
