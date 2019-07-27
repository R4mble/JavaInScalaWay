package chapter7

class Concurrent {

  def sum(ints: Seq[Int]): Int =
    ints.foldLeft(0) ((a, b) => a + b)

}
