package problems

import scala.io.Source

//noinspection SameParameterValue
object GetLines extends App {

  private def readLines(n: Int): Iterator[String] = {
    (1 to n).map(_ => scala.io.StdIn.readLine).iterator
  }

  def getLines: Iterator[String] = {
    val lines = Source.stdin.getLines()
    lines.take(2)
  }

  println(readLines(2).map(_.toInt).sum)
  println("_" * 20)

  println(getLines.map(_.toInt).sum)
  println("_" * 20)
}
