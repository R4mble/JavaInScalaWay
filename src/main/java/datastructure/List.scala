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

  def foldRight[A,B] (as: List[A], z: B) (f: (A, B) => B): B =
    as match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z) (f))
    }

  @annotation.tailrec
  def foldLeft[A,B] (as: List[A], z: B)(f: (B, A) => B): B =
    as match {
      case Nil => z
      case Cons(h, t) => foldLeft(t, f(z, h))(f)
    }

  def sum2(ns: List[Int]) =
    foldRight(ns, 0) (_ + _)

  def product2(ns: List[Double]) =
    foldRight(ns, 1.0) ((x, y) => x * y)

  def length2[A] (as: List[A]): Int =
    foldRight(as, 0) ((_, n) => n + 1)

  def sum3(ns: List[Int]) =
    foldLeft(ns, 0)(_ + _)

  def reverse[A](ns: List[A]): List[A] =
    foldLeft(ns, List[A]()) ((acc, h) => Cons(h, acc))


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
//    println(dropWhile(a, (x: Int) => x < 4))

//    val b = List(4,6,6)
    val b = List(4.0,6.0,2.0)
//    println(append(a, b))
//    println(init(a))

//    println(sum2(b))
//    println(product2(b))

//    println(foldRight(List(1,2,3,4,5), Nil:List[Int])(Cons(_,_)))

//    println(length2(List(1,3,4,5)))
//        println(sum3(a))

    println(reverse(b))

  }
}

