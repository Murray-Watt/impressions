package org.mwatt.problems

//noinspection ScalaUnusedSymbol
object SumOfOddElements {
  private def sumOfOddElements1(arr:List[Int]):Int = {
    arr.filter(x => x % 2 != 0).sum
  }

  //noinspection SimplifiableFoldOrReduce
  private def sumOfOddElements2(arr:List[Int]):Int = {
    arr.filter(x => x % 2 != 0).reduce(_ + _)
  }

  private def sumOfOddElement3(arr:List[Int]):Int = {
    arr.foldRight(0)((x,y) => {
      if (x % 2 == 0) {
        y
      } else {
        x + y
      }
    })
  }

  def main (args: Array[String]): Unit = {
    val arr = List(1, 2, 3, 4, 5)
    println(sumOfOddElements1(arr))
    println(sumOfOddElements2(arr))
    println(sumOfOddElement3(arr))
  }
}
