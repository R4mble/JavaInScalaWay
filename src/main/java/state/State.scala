package state

import State._

/**
  * 带状态的纯函数式程序: 使用函数式接受一个状态参数,在结果里一并返回一个新的状态.
  *
  * @author Wangyl
  * @date 2019/7/29
  */
case class State[S, +A] (run: S => (A, S)) {
  // functor和monad分别对应map和flatMap
  def flatMap[B] (f: A => State[S, B]): State[S, B] =
    State(s => {
      val (a, s1) = run(s)
      f(a).run(s1)
    })

  def map[B] (f: A => B): State[S, B] =
    flatMap(a => unit(f(a)))


  def map2[B, C] (sb: State[S, B]) (f: (A, B) => C): State[S, C] =
    flatMap(a => sb.map(b => f(a, b)))
}

object State {

  // 只传递状态
  def unit[S, A] (a: A): State[S, A] =
    State(s => (a, s))

  // 接收一组状态, 返回一个包含一组动作的状态
  def sequence[S, A] (sas: List[State[S, A]]): State[S, List[A]] = {
    def go(s: S, actions: List[State[S, A]], acc: List[A]): (List[A], S) =
      actions match {
        case Nil => (acc.reverse, s)
        case h :: t => h.run(s) match {
          case (a, s2) => go(s2, t, a :: acc)
        }
      }
    State((s: S) => go(s, sas, List()))
  }

  def modify[S] (f: S => S): State[S, Unit] = for {
    s <- get
    _ <- set(f(s))
  } yield ()

  def get[S]: State[S, S] = State(s => (s, s))

  def set[S] (s: S): State[S, Unit] = State(_ => ((), s))
}



