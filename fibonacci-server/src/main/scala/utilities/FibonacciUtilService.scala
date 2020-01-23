package utilities

import scala.language.postfixOps

object FibonacciUtilService {
  /* TODO:
      - replace with more simple fibonacci sequence generation solution, fancy one is not appreciated.
      - add some logging
      - add more unit tests
      - add some setup guide to the readme file
   */

  private val fibs: Stream[Int] = 0 #:: 1 #:: (fibs zip fibs.tail).map { t => t._1 + t._2 }

  /*
    Added synchronization for method in case that it will be used by multiple instances.
   */
  def getFibs(count: Int): Iterator[Int] = this.synchronized {
    (fibs takeWhile(a => a < count) toList).iterator
  }

}
