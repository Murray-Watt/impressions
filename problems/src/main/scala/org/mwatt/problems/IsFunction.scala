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

      val duplicates = pairs.groupBy(_._1).filter(_._2.size > 1)
      var isFunction = true

      for ((k, v) <- duplicates) {
        val yValues = v.map(_._2)
        if (yValues.distinct.size > 1) {
          isFunction = false
        }
      }

      println(if (isFunction) "YES" else "NO")
    }
  }

}
