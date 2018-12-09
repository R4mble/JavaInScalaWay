package buy_coffee.scala_way

class Cafe {
  def buyCoffee(cc: CreditCard): (Coffee, Charge) = {
    val cup = Coffee(5)
    (cup, Charge(cc, cup.price))
  }

  def buyCoffee(cc: CreditCard, n: Int): (List[Coffee], Charge) = {
    val purchases: List[(Coffee, Charge)] = List.fill(n) (buyCoffee(cc))
    val (coffees, charges) = purchases.unzip
    (coffees, charges.reduce((c1, c2) => c1.combine(c2)))
  }

  def coalesce(charges: List[Charge]): List[Charge] = {
    charges.groupBy(_.cc).values.map(_.reduce(_ combine _)).toList
  }
}

case class Coffee(price: Double) {
}

case class CreditCard(name: String) {
}

case class Charge(cc: CreditCard, amount: Double) {

  def combine(other: Charge): Charge = {
    if (cc == other.cc) {
      Charge(cc, amount + other.amount)
    } else {
      throw new Exception("can't combine charges to different cards")
    }
  }
}

object Cafe {
  def main(args: Array[String]): Unit = {
    val c = new Cafe()
    val cofe1 = c.buyCoffee(CreditCard("wyl"))
    val cofe2 = c.buyCoffee(CreditCard("wyl"), 2)
    val cofe3 = c.buyCoffee(CreditCard("ramble"), 3)

    val charges = c.coalesce(List[Charge](cofe1._2, cofe2._2, cofe3._2))

    println(cofe1)
    println(cofe2)
    println(cofe3)
    println(charges)
  }
}
