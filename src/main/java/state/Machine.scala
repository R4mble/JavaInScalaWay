package state

import state.State._

/**
  * @author Wangyl
  * @date 2019/7/29
  */
sealed trait Input
case object Coin extends Input
case object Turn extends Input
case class Machine(locked: Boolean, candies: Int, coins: Int)

object Candy {
  type coins = Int
  type candies = Int

  def update: Input => Machine => Machine =
    (input: Input) => (machine: Machine) => (input, machine) match {
      case (_, Machine(_, 0, _)) => machine
      case (Coin, Machine(true, _, _)) => machine
      case (Turn, Machine(false, _, _)) => machine
      case (Coin, Machine(false, candy, coin)) =>
          Machine(true, candy, coin + 1)
      case (Turn, Machine(true, candy, coin)) =>
          Machine(false, candy - 1, coin)
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
  val s0 = Candy.simulateMachine(List(Coin, Turn,Coin, Turn,Coin, Turn)).run(m)
  println(s0)

  val s= Candy.simulateMachine(List(Coin, Turn, Turn, Turn, Coin, Coin, Turn, Coin, Coin, Turn)).run(Machine(false, 5, 5))
  println(s)

  val s1 = State.get.flatMap((_: Int) => State((s: Int) => (s, s))).run(5)
  println(s1)
}

