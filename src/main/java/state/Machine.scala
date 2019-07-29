package state

import State._

/**
  * @author Wangyl
  * @date 2019/7/29
  */
sealed trait Input
case object Coin extends Input
case object Turn extends Input
case class Machine(locked: Boolean, candies: Int, coins: Int)

object candy {
  type coins = Int
  type candies = Int

  def update: Input => Machine => Machine =
    (input: Input) => (machine: Machine) => (input, machine) match {
      case (_, Machine(_, 0, _)) => machine
      case (Coin, Machine(true, _, _)) => machine
      case (Turn, Machine(false, _, _)) => machine
      case (Coin, Machine(false, candy, coin)) =>
          Machine(false, candy, coin + 1)
      case (Turn, Machine(true, candy, coin)) =>
          Machine(true, candy - 1, coin)
    }

  /**
    *  for循环中的 yield 会把当前的元素记下来，保存在集合中，循环结束后将返回该集合。
    *  <- 集合遍历
    */
  def simulateMachine(inputs: List[Input]): State[Machine, (candies, coins)] = for {
    _ <- sequence(inputs map (modify[Machine] _ compose update))
    machine <- get
  } yield (machine.coins, machine.candies)
}

object test extends App {
  val m = Machine(false, 10, 4)
  val s = candy.simulateMachine(List(Coin, Turn))
//  val s1 = candy.update(Coin)(m)
//  println(s1)
}

