package problems

object AreaAndVolumeUnderACurve {
  def f(coefficients: List[Int], powers: List[Int], x: Double): Double = {
    val terms = coefficients.zip(powers).map { case (a, b) =>
      a.toDouble * Math.pow(x, b.toDouble) / 1000
    }
    terms.sum
  }

  def area(coefficients: List[Int], powers: List[Int], x: Double): Double = {
    val fx = f(coefficients, powers, x)
    Math.PI * fx * fx * 1000
  }

  def tabulate(start: Int,end: Int): List[Double] = {
    val stepCount = (end - start) * 1000
    List.tabulate(stepCount)(i => start.toDouble + i.toDouble * 0.001)
  }
  def summation(func:(List[Int],List[Int],Double)=>Double,upperLimit:Int,lowerLimit:Int,coefficients:List[Int],powers:List[Int]):Double =
  {
    val xs = tabulate(lowerLimit,upperLimit)
    xs.map(x => func(coefficients, powers, x)).sum
  }

  def main(args: Array[String]): Unit = {
    val coefficients = List(1, 2, 3, 4, 5)
    val powers = List(6, 7, 8, 9, 10)
    val lowerLimit = 1
    val upperLimit = 4

    val areaUnderCurve = summation(f, upperLimit, lowerLimit, coefficients, powers)
    println("Area under the curve: " + areaUnderCurve)

    val volumeOfRevolution = summation(area, upperLimit, lowerLimit, coefficients, powers)
    println("Volume of revolution: " + volumeOfRevolution)
  }
}
