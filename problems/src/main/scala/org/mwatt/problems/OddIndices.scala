package org.mwatt.problems

//noinspection ScalaUnusedSymbol
object OddIndices {
  private def removeEvenIndices(arr:List[Int]):List[Int] = {
    val indices = 1 until arr.size by 2
    for {
      i  <- indices.toList
    }  yield {
      arr(i)
    }
  }

  private def removeEvenIndices2(arr:List[Int]):List[Int] = {
    arr.zipWithIndex.filter(_._2 % 2 != 0).map(_._1)
  }

  private def removeEvenIndices3(arr:List[Int]):List[Int] = {
    arr.zipWithIndex.collect { case (x, i) if i % 2 != 0 => x }
  }

  private def removeEvenIndices4(arr:List[Int]):List[Int] = {
    arr.tail.toIterator.sliding(1, 2).toList.flatten
  }

  def main(args: Array[String]): Unit = {
    val arr = List(1, 2, 3, 4, 5)
    println(removeEvenIndices(arr))
    println(removeEvenIndices2(arr))
    println(removeEvenIndices3(arr))
    println(removeEvenIndices4(arr))
  }
}
