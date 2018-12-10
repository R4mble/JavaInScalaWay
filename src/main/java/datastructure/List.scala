package datastructure

//sealed意味着这个trait的所有实现都必须定义在这个文件里
sealed trait List[+A] {
}
//+ 协变.   A是B的子类,那么List[A]是List[B]的子类

case object Nil extends List[Nothing]

case class Cons[+A] (head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product(ds: List[Int]): Int = ds match {
    case Nil => 1
    case Cons(0, _) => 0
    case Cons(x, xs) => x * product(xs)
  }

  def tail[A](ds: List[A]): List[A] = ds match {
    case Nil => sys.error("tail of empty list")
    case Cons(_, t) => t
  }

  def setHead[A](ds: List[A], h: A): List[A] = ds match {
    case Nil => sys.error("tail of empty list")
    case Cons(_, t) => Cons(h, t)
  }

  def drop[A] (l: List[A], n: Int): List[A] =
    if (n <= 0) l
    else l match {
      case Nil => Nil
      case Cons(_, t) => drop(t, n - 1)
  }

  def dropWhile[A](l: List[A], f: A => Boolean): List[A] =
    l match {
      case Cons(h,t) if f(h) => dropWhile(t, f)
      case _ => l
    }

  def append[A] (a1: List[A], a2: List[A]): List[A] = a1 match {
    case Nil => a2
    case Cons(h, t) => Cons(h, append(t, a2))
  }

  def init[A] (l: List[A]): List[A] = l match {
    case Nil => sys.error("init of empty list")
    case Cons(_, Nil) => Nil
    case Cons(h, t) => Cons(h, init(t))
  }

  //可变参数,可以是一个或多个该类型的参数
  def apply[A] (as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))


  def main(args: Array[String]): Unit = {
    val a = List(1,2,3,4,5)
//    println(sum(a))
//    println(product(a))
//    println(tail(a))
//    println(setHead(a, 123))
//    println(drop(a, 2))
    println(dropWhile(a, (x: Int) => x < 4))

    val b = List(4,6,6)
    println(append(a, b))
    println(init(a))

  }
}

