package game.util

import scala.reflect.ClassTag


class Random[T: ClassTag] {
  def shuffle(a: Array[T]): Array[T] = util.Random.shuffle(a).toArray[T]
}

object Random {
  def apply[T: ClassTag]: Random[T] = new Random()
}
