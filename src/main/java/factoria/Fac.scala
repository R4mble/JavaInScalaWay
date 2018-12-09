package factoria

object Fac {

  def factorial(n: Int): Int = {
    @annotation.tailrec
    def go(n: Int, acc: Int): Int =
      if (n <= 0) acc
      else go(n - 1, n * acc)

    go(n, 1)
  }

  //done by myself, pretty cool
  def fib(n: Int): Int = {
    @annotation.tailrec
    def go(f: Int, l: Int, n: Int): Int = {
      if (n <= 0) l
      else go(l, f + l, n - 1)
    }
    go(0, 1, n)
  }

  def findFirst[A] (as: Array[A], p: A => Boolean): Int = {
    @annotation.tailrec
    def loop(n: Int): Int =
      if (n >= as.length) -1
      else if (p(as(n))) n
      else loop(n + 1)

    loop(0)
  }

  def isSorted[A] (as: Array[A], ordered: (A, A) => Boolean): Boolean = {

  }

  def formatResult(name: String, n: Int, f: Int => Int) = {
    val msg = "The %s of %d is %d."
    msg.format(name, n, f(n))
  }

  def main(args: Array[String]): Unit = {
//   println(factorial(4))
//    println(fib(5))

    println(formatResult("factorial", 7, factorial))
    println(formatResult("fib", 7, fib))
  }
}
