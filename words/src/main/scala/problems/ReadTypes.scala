package problems

object ReadTypes extends App {
  private val anInt = 1 // readInt()
  println(s"Int: $anInt")

  private val aLong = 200 // readLong()
  println(s"Long: $aLong")

  private val aDouble = 10 // readDouble()
  println(s"Double: $aDouble")

  private val aBoolean = true // readBoolean()
  println(s"Boolean: $aBoolean")

  private val aChar = 'c' // readChar()
  println(s"Char: $aChar")

  private val aFloat = 10.4 // readFloat()
  println(s"Float: $aFloat")

  private val aShort = 10 // readShort()
  println(s"Short: $aShort")


  println("Enter your name and age: ")

  private val input = "Murray 10" // readf("%s %d")
  val name = input.head
  private val age = input.tail.head

  println(s"Hello, $name! You are $age years old.")
}
