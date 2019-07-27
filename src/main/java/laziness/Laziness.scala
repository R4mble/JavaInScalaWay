package laziness

object Laziness {
  // 惰性求值
  def if2[A] (cond: Boolean, onTrue: () => A, onFalse: () => A): A =
    if (cond) onTrue() else onFalse()

  // 在非严格求值参数的地方传入一个无参函数, 然后在方法体里显式地调用这个函数获取结果.
  // Scala语法糖
  def if3[A] (cond: Boolean, onTrue: => A, onFalse: => A): A =
    if (cond) onTrue else onFalse


  def maybeTwice(b: Boolean, i: => Int): Int = if (b) i+i else 0

  def lazyOnce(b: Boolean, i: => Int): Int = {
    lazy val j = i
    if (b) j+j else 0
  }

  def main(args: Array[String]): Unit = {
    val a = 4
    if2(a < 22, () => println("a"), () => println("b"))
    if3(a < 22, println("a"), println("b"))

    // hi会被打印两次, 因为i被计算了两次
    val x = maybeTwice(true, {println("hi"); 1+21})
    println(x)

    // hi会被打印一次, 当lazy变量被引用时,会缓存结果,后续引用的地方不会触发重复求值
    // Scala中非严格求值的函数接受的参数是传名参数,而非传值参数
    val y = lazyOnce(true, {println("hi"); 1+21})
    println(y)
  }
}
