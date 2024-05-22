package problems

object Lambdas {
  def filterLongWords(strings: List[String], lenFilter: Int) : List[String] = {
    strings.filter(_.length > lenFilter)
  }

  def filterLongPalindromes(strings: List[String], lenFilter: Int) : List[String] = {
    strings.filter(x => x.length > lenFilter && x == x.reverse)
  }

  def duplicates(ints: List[Int]) : List[Int] = {
    // ints.groupBy(identity).collect { case (x, List(_,_,_*)) => x }.toList
    ints.foldRight(List.empty[Int])((x, acc) => if (ints.count(_ == x) > 1 && !acc.contains(x)) x :: acc else acc)
  }

  def customReduce[A](cr: (A, A) => A, list: List[A], defaultUni: A, defaultForEmpty: A): A = {
    list match {
      case Nil => defaultForEmpty
      case List(_) => defaultUni
      case _ => list.reduce(cr)
    }
  }

  def multiplyElements(list: List[Int], default: Int) : BigInt = {
    val bigIntList = list.map(BigInt(_))

    val defaultUni = if (bigIntList.size == 1) bigIntList.head else BigInt(1)
    customReduce[BigInt](cr = (x, y) => x * y, list = bigIntList,  defaultUni = defaultUni , defaultForEmpty = BigInt(default))
  }
}
