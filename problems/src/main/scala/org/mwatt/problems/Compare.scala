package org.mwatt.problems

object Compare {

  val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val sumOfEvens1 = numbers.filter(_ % 2 == 0).reduce((a, b) => a + b)

  println("sumOfEvens: " + sumOfEvens1)

  val sumOfEvens2 = numbers.filter(_ % 2 == 0).reduce(_ + _)
  println("sumOfEvens2: " + sumOfEvens2)

  /*
  def sumOfSquaresOfEvens(numbers: List[Int]): Int = {
    numbers.filter(num => num % 2 == 0)
      .map(_ * _)
      .sum
  }
  */

  def sumOfSquaresOfEvens2(numbers: List[Int]): Int = {
    println("numbers: " + numbers)
    println("numbers.filter(_ % 2 == 0): " + numbers.filter(_ % 2 == 0))
    println("numbers.filter(_ % 2 == 0).map(num => num * num): " + numbers.filter(_ % 2 == 0).map(num => num * num))

    numbers.filter(_ % 2 == 0)
      .map(num => num * num)
      .sum
  }
}
