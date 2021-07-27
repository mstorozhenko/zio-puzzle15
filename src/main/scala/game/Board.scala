package game

import game.util.ZeroIndex

trait Board[T] extends MoveControl[T] {
  var zeroElementIndex: ZeroIndex

  val zero: T

  def board: Array[Array[T]]

  def balanced: Boolean

  def size: Int
}
