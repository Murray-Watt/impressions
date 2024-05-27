package org.mwatt.problems

object FilterImpl {

  private def filterWithFoldRight[A](predicate: A => Boolean, list: List[A]): List[A] =
    list.foldRight(List.empty[A])((element, accumulator) =>
      if (predicate(element))
        element :: accumulator
      else
        accumulator
    )

  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5)
    println(filterWithFoldRight((x: Int) => x < 4, list))
  }
}
