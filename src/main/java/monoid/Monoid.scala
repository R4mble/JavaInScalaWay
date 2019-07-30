package monoid

import scala.collection.immutable.Stream.Empty

/**
  * @author Wangyl
  * @date 2019/7/30
  */
trait Monoid[A] {
  // op(op(x,y),z) == op(x,op(y,z))
  def op(a1: A, a2: A): A
  // op(x,zero) == x   op(zero,x) == x
  def zero: A
}

object Test extends App {
  val stringMonoid = new Monoid[String] {
    def op(a1: String, a2: String) = a1 + a2
    def zero = ""
  }

  def listMonoid[A] = new Monoid[List[A]] {
    def op(a1: List[A], a2: List[A]) = a1 ++ a2
    def zero = Nil
  }

  val intAddition = new Monoid[Int] {
    def op(a: Int, b: Int) = a + b
    def zero = 0
  }

  val intMultiplication = new Monoid[Int] {
    def op(a: Int, b: Int) = a * b
    def zero = 1
  }

  val booleanOr = new Monoid[Boolean] {
    def op(a: Boolean, b: Boolean) = a || b
    def zero = false
  }

  val booleanAnd = new Monoid[Boolean] {
    def op(a: Boolean, b: Boolean) = a && b
    def zero = true
  }

  // 不满足交换律
  def optionMonoid[A] = new Monoid[Option[A]] {
    def op(a: Option[A], b: Option[A]) = a orElse b
    def zero = None
  }

  // 参数和返回值类型相同的函数：自函数
  def endoMonoid[A] = new Monoid[A => A] {
    def op(a: A => A, b: A => A) = a compose b
    def zero = (a: A) => a
  }

  def concatenate[A] (as: List[A], m: Monoid[A]): A =
    as.foldLeft(m.zero)(m.op)

  def foldMap[A, B] (as: List[A], m: Monoid[B]) (f: A => B): B =
    as.foldLeft(m.zero)((b, a) => m.op(b, f(a)))

  val words = List("a", "b", "c")
  val s0 = words.foldRight(stringMonoid.zero)(stringMonoid.op)
  val s1 = words.foldLeft(stringMonoid.zero)(stringMonoid.op)
  val s3 = words.foldLeft("")(_ + _)
  val s4 = words.foldRight("")(_ + _)
  println(s0)
  println(s1)
  println(s3)
  println(s4)
}
