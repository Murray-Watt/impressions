package org.mwatt.problems

object Lambdas2 {
  /*
  Problem: Create a lambda function in Scala that takes an integer x and returns the result of x * 2.
  Then, apply this lambda function to the input 4 and return the result.
  */

   private def problem1() : Unit = {
     val f : Int => Int = x => x * 2
     println(s"problem 1: ${f(4)}")
   }


  /*
    Problem: Create lambda functions addOne and addTwo in Scala that take an integer x and return the result of x + 1 and x + 2, respectively.
    Then, create a composite function addThree by composing addOne and addTwo, and apply it to the input 3. Return the result.
   */

  private def problem2() : Unit = {

    val addOne : Int => Int = x => x + 1
    val addTwo : Int => Int = x => x + 2
    val addThree : (Int => Int, Int => Int, Int)  => Int = (f1, f2, x) => f2(f1(x))
    println(s"problem 2: ${addThree(addOne,addTwo, 3)}")
  }

  /*
    Problem: In Scala, create lambda functions to represent the natural numbers zero, one, two, and three using Church encoding.
    Then, create a function churchNumber that takes a lambda function f and an integer n, and applies f n times to a given input value.
    Finally, apply churchNumber with the lambda function one and 47 as inputs to emulate the behavior of the lambda calculus expression λx.λy.x47y.

    This problem builds on the previous ones by introducing Church encoding to represent natural numbers and requires you to apply
    a given lambda function multiple times using another function.
   */

  private def problem3() : Unit = {
    val zero: (Int => Int) => Int => Int = _ => x => x
    val one: (Int => Int) => Int => Int = f => x => f(zero(f)(x))
    val two: (Int => Int) => Int => Int = f => x => f(one(f)(x))
    val three: (Int => Int) => Int => Int = f => x => f(two(f)(x))

    val nat: Int => (Int => Int, Int) => Int = n => {
      def loop(k: Int)(f: Int => Int)(x: Int): Int =
        if (k == 0) x else f(loop(k - 1)(f)(x))

      (f, x) => loop(n)(f)(x)
    }

    val fChurchN: (Int => Int, Int) => Int => Int = (f, n) => x => nat(n)(f, x)
    val fChurch47: (Int => Int) => Int => Int = f => fChurchN(f, 47)



    println(s"problem 3 - zero: ${zero(_ => 0)(3)}")
    println(s"problem 3 - one: ${one(x => x * x)(3)}")
    println(s"problem 3 - two: ${two(x => x * x)(3)}")
    println(s"problem 3 - three: ${three(x => x * x)(3)}")
    println(s"problem 3 - fChurch47: ${fChurch47(x => x + 1)(3)}")
  }

  /*
    Problem: In Scala, create a lambda function selfApply that takes two arguments: a binary function f and an input value x.
    The lambda function should return a new function that takes another input value y and applies f to x and the result of applying f to x and y.
    Then, apply selfApply with a suitable binary function f and an input value x,
    and demonstrate the resulting function's behavior on another input value y.

    val selfApply: ((A => A => A) => A) => A => A = f => x => y => f(x, f(x, y))

    Next, consider how to choose a suitable binary function f and an input value x to apply with selfApply.
    Finally, demonstrate the resulting function's behavior on another input value y.

   */

  private def problem4() : Unit = {
    val selfApply: ((Int, Int) => Int, Int) => Int => Int = (f, x) => y => f(x, f(x, y))

    val sum : (Int, Int) => Int = (x, y) => x + y
    val incrementLike: Int => Int = selfApply(sum, 1)
    val result = incrementLike(2)

    println(s"problem 4: $result")
  }

  /*
  Problem: In Scala, create a lambda function identity that takes two arguments: an input value x and another input value y.
  The lambda function should return the second input value y, effectively behaving as an identity function that ignores the
  first input value. Then, apply identity with suitable input values x and y, and demonstrate that the resulting output
  is indeed equal to the second input value y.


  This problem builds on the previous ones by introducing the concept of an identity function using lambda functions.
  Think about defining your identity lambda function using the following syntax:

  val identity: A => B => B = x => y => y
  Next, consider how to choose suitable input values x and y to apply with identity.
  Finally, demonstrate that the resulting output is equal to the second input value y.
   */

  private def problem5() : Unit = {
    val identity: Int => Int => Int = _ => y => y
    val result = identity(1)(2)
    println(s"problem 5: $result")
  }

  def main(args: Array[String]) :  Unit = {
    problem1()
    problem2()
    problem3()
    problem4()
    problem5()
  }
}
