package org.mwatt.problems

case class ShoppingCartItem(name: String, price: Double, quantity: Int)

object ShoppingCurrying {

  def calculateTotal(items: List[ShoppingCartItem]): Double = {
    items.map(item => item.price * item.quantity).sum
  }

  def calculateTotalWithDiscount(discount: Double)(items: List[ShoppingCartItem]): Double = {
    val total = calculateTotal(items)
    total - total * discount
  }

  def calculateTotalWithDiscountAndTax(discount: Double, tax: Double)(items: List[ShoppingCartItem]): Double = {
    val total = calculateTotal(items)
    val discountedTotal = total - total * discount
    discountedTotal + discountedTotal * tax
  }

  def main(args: Array[String]): Unit = {
    val items = List(
      ShoppingCartItem("apple", 1.0, 2),
      ShoppingCartItem("banana", 2.0, 3),
      ShoppingCartItem("cherry", 3.0, 4)
    )

    val total = calculateTotal(items)
    println(s"Total: $total")

    val totalWithDiscount = calculateTotalWithDiscount(0.1)(items)
    println(s"Total with discount: $totalWithDiscount")

    val totalWithDiscount2 = calculateTotalWithDiscount(0.2) _
    val x = totalWithDiscount2(items)
    println(s"Total with membership: $x")

    val totalWithDiscountAndTax = calculateTotalWithDiscountAndTax(0.1, 0.1)(items)
    println(s"Total with discount and tax: $totalWithDiscountAndTax")
  }
}

