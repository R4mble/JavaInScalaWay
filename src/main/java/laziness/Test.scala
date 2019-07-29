package laziness


object Test extends App{
//    val st = Stream(1,2,3,4,5,6)
//    println(st.headOption.get)
//    println(st.take(3).toList)
//    println(st.drop(2).toList)
//    println(st.takeWhile(_ < 4).toList)
//    println(st.exists(_ == 3))
//    println(st.existsFoldRight(_ == 3))
//
//    println(st.forAll(_ > 0))
//    println(st.takeWhileFoldRight(_ < 3).toList)
//    println(st.headOptionFoldRight.get)

  // 不能在main方法里执行, 只能让类继承App
//  val ones: Stream[Int] = Stream.cons(1, ones)
//
//  println(ones.take(4).toList)
//  println(ones.exists(_ % 2 != 0))
//
//  val twos = Stream.constant(2)
//  println(twos.take(4).toList)
//
//  val from5 = Stream.from(5)
//  println(from5.take(4).toList)
//
//  val fibs = Stream.fibs
//  println(fibs.take(10).toList)
//
//  val fibs2 = Stream.fibsUnfold
//  println(fibs2.take(10).toList)

  val st = Stream(1,2,3,4,5,6)
//  println(st.map(_ + 2).toList)
//  println(st.mapFoldRight(_ + 2).toList)
//  println(st.mapUnFold(_ + 2).toList)
//
//  println(st.takeUnFold(3).toList)
//  val add = (a: Int, b: Int) => a + b
//  println(st.zipWith(Stream(6, 5, 4, 3, 2, 1))(_ + _).toList)
//
//  println(Stream(1,2,3).zip(Stream(4,5,6)).toList)

  println(Stream.unfold(false))

}
