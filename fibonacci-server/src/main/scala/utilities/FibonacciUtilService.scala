package utilities

import scala.language.postfixOps

object FibonacciUtilService {
  private val fibs: Stream[Int] = 0 #:: 1 #:: (fibs zip fibs.tail).map { t => t._1 + t._2 }

  /*
    Added synchronization for method in case that it will be used by multiple instances.
   */
  def getFibs(count: Int): Iterator[Int] = this.synchronized {
    (fibs takeWhile(a => a < count) toList).iterator
  }

}
