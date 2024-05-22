package problems

object EToX {
  def eToX1(x: Int): Double = {
    val e = 2.71828
    Math.pow(e, x)
  }

  def factorial(i: Int) : Double = {
    if (i == 0) {
      BigInt(1).toDouble
    } else {
      (1 to i).foldLeft(BigInt(1))((a, e) => a * BigInt(e)).toDouble
    }
  }

  def eToX(x: Double): Double = {
    val terms = for {
      i <- 1 to 9
    } yield {
      Math.pow(x,i) / factorial(i).toDouble
    }

    1 + terms.sum
  }

  def main(args: Array[String]): Unit = {
    println(eToX1(5))
    println(eToX(5))   // To 10 term s for the problem
  }
}
