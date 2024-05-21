package problems

object HelloWorldNApp extends App {

  private def helloWorldForN(n: Int): Unit = {
    for (_ <- 1 to n) {
      println("Hello World")
    }
  }

  private def helloWorldRangeN(n: Int): Unit = {
    (1 to n).foreach(_ => println("Hello World"))
  }

  var n = scala.io.StdIn.readInt

  println("Hello World")
  println("_" * 20)

  helloWorldForN(n)
  println("_" * 20)

  helloWorldRangeN(n)

}
