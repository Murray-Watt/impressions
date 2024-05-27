package org.mwatt.problems

import scala.io.Source

//noinspection SameParameterValue
object Sum2Lines extends App {

  private def readNLines(n: Int): Iterator[String] = {
    (1 to n).map(_ => scala.io.StdIn.readLine).iterator
  }

  private def get2Lines: Iterator[String] = {
    val lines = Source.stdin.getLines()
    lines.take(2)
  }

  println(readNLines(2).map(_.toInt).sum)
  println("_" * 20)

  println(get2Lines.map(_.toInt).sum)
  println("_" * 20)
}
