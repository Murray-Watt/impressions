package problems

import problems.FilterImpl.filterWithFoldRight

object CountWithoutCount {
  def count[T](list: List[T]): Int = list.foldLeft(0)((acc, _) => acc + 1)

  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5)
    println(count(list))
  }
}
