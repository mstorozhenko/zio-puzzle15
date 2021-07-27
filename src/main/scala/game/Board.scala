package game

import game.util.Index2D

trait Board[T] extends MoveControl[T] {
  var zeroElementIndex: Index2D

  val zero: T

  def board: Array[Array[T]]

  def balanced: Boolean

  def size: Int
}
