package utilities

object Fibonacci {
  val fibs: Stream[Int] = 0 #:: 1 #:: (fibs zip fibs.tail).map { t => t._1 + t._2 }

  def getFibs(count: Int): String = {
    (fibs takeWhile(a => a < count) toList) toString
  }

}
