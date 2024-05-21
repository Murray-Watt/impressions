package problems

//noinspection ScalaUnusedSymbol
object ReverseWithoutReverse {
  private def reverse1[T](list: List[Int]): List[Int] = list.foldLeft(List[Int]())((x, y) => y :: x)
  private def reverse2[T](list: List[Int]): List[Int] = list.foldRight(List[Int]())((y, x) => x :+ y)

  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5)
    println(reverse1(list))
    println(reverse2(list))
  }
}
