package state

import State._

// 有点像unfold函数
case class State[S, +A] (run: S => (A, S)) {

  def flatMap[B] (f: A => State[S, B]): State[S, B] =
    State(s => {
      val (a, s1) = run(s)
      f(a).run(s1)
    })

  def map[B] (f: A => B): State[S, B] =
    flatMap(a => unit(f(a)))

  def map2[B,C](sb: State[S,B])(f: (A,B) => C): State[S,C] =
    flatMap(a => sb.map(b => f(a, b)))
}

object State {

  // 只传递状态
  def unit[S, A] (a: A): State[S, A] =
    State(s => (a, s))
}
