package problems

import scala.io.Source

object ReplicateNums {
  private def replicateNums(n: Int, nums: List[Int]) : List[Int] = {
    val numList = for {
      num <- nums
      _ <- 1 to n
    } yield {
      num
    }

    numList
  }

def main(args: Array[String]): Unit = {
    val n = Source.stdin.getLines.next().toInt
    val restOfLines = Source.stdin.getLines.takeWhile(line => line != null && line.nonEmpty).toList
    val nums = restOfLines.map(_.toInt)
    val result = replicateNums(n, nums)
    println(result.mkString("\n"))
  }
}
