package problems

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class LambdasTest extends FunSuite {

  test("filter to long words") {
    val result = Lambdas.filterLongWords(strings = List("apple", "banana", "cherry", "dragonfruit"), lenFilter = 5)
    result should contain theSameElementsAs List("banana", "cherry", "dragonfruit")
  }

  test("filter to long words palindromes") {
    val result = Lambdas.filterLongPalindromes(strings = List("racecar", "apple", "banana", "cherry", "dragonfruit"), lenFilter = 5)
    result should contain theSameElementsAs List("racecar")
  }

  test("return a list of duplicates in a list") {
    val result = Lambdas.duplicates(ints = List(1, 2, 3, 4, 5, 1, 2, 3, 4, 5))
    result should contain theSameElementsAs List(1, 2, 3, 4, 5)
  }

  test("multiple elements with custom reduce") {
    {
      val result = Lambdas.multiplyElements(list = List(1, 2, 3, 4, 5), default = 1)
      result should be(120)
    }

    {
      val result = Lambdas.multiplyElements(list = List(1, 2, 3), default = 1)
      result should be(6)
    }

    {
      val result = Lambdas.multiplyElements(list = List(2, 4, 6), default = 1)
      result should be(48)
    }

    {
      val result = Lambdas.multiplyElements(list = List(-1, -2, -3), default = 1)
      result should be(-6)
    }

    {
      val result = Lambdas.multiplyElements(List(), 1)
      result should be(1)
    }

    {
      val result = Lambdas.multiplyElements(List(16), 1)
      result should be(16)
    }


    {
      val aBigInt = (BigInt(Int.MaxValue)/BigInt(10000)).toInt
      val result = Lambdas.multiplyElements(List(aBigInt,2,5), 1)
      result should be(aBigInt * 10)
    }
  }
}
