package com.madimaxi.puzzle15.game

import izumi.reflect.Tag
import zio.{Has, Task, UIO, ZIO}

trait BoardControl[T] {
  /**
   * Initialize new board with defined size
   */
  def init(size: Int, initMode: InitMode = Shuffled): Task[Board[T]]

  /**
   * Move tile to zero element
   *
   * @param move direction
   * @return true when move was winning, false - to continue com.madimaxi.puzzle15.game
   */
  def moveTile(move: MoveDirection, board: Board[T]): Task[Board[T]]

  /**
   * Check winning condition
   *
   * @param ord Correct ordering for given T
   */
  def completed(board: Board[T])(implicit ord: Ordering[T]): Task[Boolean] = Task.succeed {
    val boardVal = board.value
    if (boardVal.last.indexOf(Empty) == boardVal.last.size - 1) // if empty tile not in last position - board incomplete
      boardVal.flatten.sortWith {
        case (Value(v1), Value(v2)) => ord.lteq(v1, v2)
        case (Empty, Value(_)) => false
        case _ => true
      } == boardVal.flatten
    else false
  }
}

object BoardControl {
  def init[T: Tag](size: Int, initMode: InitMode = Shuffled): ZIO[Has[BoardControl[T]], Throwable, Board[T]] =
    ZIO.serviceWith[BoardControl[T]](_.init(size, initMode))

  def moveTile[T: Tag](move: MoveDirection, board: Board[T]): ZIO[Has[BoardControl[T]], Throwable, Board[T]] =
    ZIO.serviceWith[BoardControl[T]](_.moveTile(move, board))

  def completed[T: Tag](board: Board[T])(implicit ord: Ordering[T]): ZIO[Has[BoardControl[T]], Throwable, Boolean] =
    ZIO.serviceWith[BoardControl[T]](_.completed(board))
}
