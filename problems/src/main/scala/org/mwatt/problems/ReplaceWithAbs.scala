package org.mwatt.problems

class ReplaceWithAbs {
    private def replaceWithAbs(list: List[Int]): List[Int] = list.map(x => Math.abs(x))

    def main(args: Array[String]): Unit = {
      val list = List(1, -2, 3, -4, 5)
      println(replaceWithAbs(list))
    }
}
