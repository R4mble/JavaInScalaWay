package laziness

// 不import的话, 找不到object Stream里的方法
import Stream._

trait Stream[+A] {
  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, t) => Some(h())
  }

  def toList: List[A] = {
    def go(s: Stream[A], acc: List[A]): List[A] = s match {
      case Cons(h, t) => go(t(), h() :: acc)
      case _ => acc
    }
    go(this, List()).reverse
  }

  def take(n: Int): Stream[A] = this match {
    case Cons(h, t) if n > 1 => cons(h(), t().take(n - 1))
    case Cons(h, _) if n == 1 => cons(h(), empty)
    case _ => empty
  }

  def drop(n: Int): Stream[A] = this match {
    case Cons(_, t) if n > 0 => t().drop(n - 1)
    case _ => this
  }

  // 从起始元素连续满足
  def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Cons(h, t) if p(h()) => cons(h(), t() takeWhile p)
    case _ => empty
  }

  def exists(p: A => Boolean): Boolean = this match {
    case Cons(h, t) => p(h()) || t().exists(p)
    case _ => false
  }

  // foldRight版的exists
  def existsFoldRight(p: A => Boolean): Boolean =
    foldRight(false) ((a, b) => p(a) || b)

  def forAll(p: A => Boolean): Boolean =
    foldRight(true) ((a, b) => p(a) && b)

  def takeWhileFoldRight(p: A => Boolean): Stream[A] =
    foldRight(empty[A]) ((h, t) =>
        if (p(h)) cons(h, t)
        else empty)

  def headOptionFoldRight: Option[A] =
    foldRight(None: Option[A]) ((a, _) => Some(a))

  // 自己实现的原生map
  def map[B] (f: A => B): Stream[B] = this match {
    case Cons(h, t) => cons(f(h()), t().map(f))
    case _ => empty
  }

  def mapFoldRight[B] (f: A => B): Stream[B] =
    foldRight(empty[B]) ((h, t) => cons(f(h), t))

  def mapUnFold[B] (f: A => B): Stream[B] =
    unfold(this) {
      case Cons(h, t) => Some((f(h()), t()))
      case _ => None
    }

  def takeUnFold(n: Int): Stream[A] =
    unfold(this, n) {
      case (Cons(h, _), 1) => Some((h(), (empty, 0)))
      case (Cons(h ,t), n) if n > 1 => Some((h(), (t(), n - 1)))
      case _ => None
    }

  def takeWhileUnFold(f: A => Boolean): Stream[A] =
    unfold(this) {
      case Cons(h, t) if f(h()) => Some(h(), t())
      case _ => None
    }

  def zipWith[B,C] (s2: Stream[B]) (f: (A,B) => C): Stream[C] =
    unfold((this, s2)) {
      case (Cons(h1, t1), Cons(h2, t2)) =>
        Some((f(h1(), h2()), (t1(), t2())))
      case _ => None
    }

  def zip[B] (s2: Stream[B]): Stream[(A, B)] =
    zipWith(s2)((_, _))



//  def startsWith[A] (s: Stream[A]): Boolean =
//    unfold()








  def filter(f: A => Boolean): Stream[A] =
    foldRight(empty[A]) ((h, t) =>
      if (f(h)) cons(h ,t) else t)

  // B是A的父类型
  def append[B>:A] (s: => Stream[B]): Stream[B] =
    foldRight(s) ((h, t) => cons(h ,t))

  def flatMap[B] (f: A => Stream[B]): Stream[B] =
    foldRight(empty[B]) ((h, t) => f(h) append t)

  def find(p: A => Boolean): Option[A] =
    filter(p).headOptionFoldRight

  /**
    * @param f f参数的类型声明中B类型前面前头表示第二个参数是传名参数,
    *          f不会对它进行求值
    */
  def foldRight[B] (z: => B) (f: (A, => B) => B): B =
    this match {
        // 如果f不对第二个参数进行求值,递归就不会发生
      case Cons(h, t) => f(h(), t().foldRight(z) (f))
      case _ => z
    }



}

case object Empty extends Stream[Nothing]
case class Cons[+A] (head: () => A, tail: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A] (h: => A, t: => Stream[A]): Stream[A] = {
    lazy val head = h
    lazy val tail = t
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  //  def constant[A] (a: A): Stream[A] =
//    cons(a, constant(a))

  // 效率比上面那种高, 因为只是一个引用自己的对象
  def constant[A] (a: A): Stream[A] = {
    lazy val tail: Stream[A] = Cons(() => a, () => tail)
    tail
  }

  def from(n: Int): Stream[Int] =
    cons(n, from(n + 1))

  def fibs: Stream[Int] = {
    def go(pre: Int, cur: Int): Stream[Int] =
      cons(pre, go(cur, pre + cur))
    go(0, 1)
  }

  /**
    * 构造流的函数, 接收一个初始状态,
    * 以及一个在生成的Stream中用于产生下一状态和下一个值的函数
    *
    * 共递归函数的例子.
    * 递归函数由不断地对更小范围的输入参数进行递归调用而结束.
    * 而共递归函数只要保持生产数据不需要结束, 可以在有限的时间段里对更多的结果求值.
    * unfold函数生产能力随f函数的结束而结束,
    *
    * @param z 初始状态
    * @param f 产生下一状态和下一个值的函数
    * @tparam A 值类型
    * @tparam S 状态类型
    * @return 流
    */
  def unfold[A, S] (z: S) (f: S => Option[(A, S)]): Stream[A] =
    f(z) match {
      case Some((h, s)) => cons(h, unfold(s)(f))
      case None => empty
    }

  val fibsUnfold: Stream[Int] =
    unfold((0, 1)) {
      case (pre, cur) => Some((pre, (cur, pre + cur)))
    }

  def fromUnfold(n: Int): Stream[Int] =
    unfold(n)(n => Some((n,n+1)))

  def constantUnfold[A] (a: A): Stream[A] =
    unfold(a) (_ => Some((a, a)))

  def onesUnfold: Stream[Int] = constantUnfold(1)



  // 可变参方法
  def apply[A] (as: A*): Stream[A] =
    if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))
}

