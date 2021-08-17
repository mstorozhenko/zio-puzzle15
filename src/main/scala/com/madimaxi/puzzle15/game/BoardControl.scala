package com.madimaxi.puzzle15.game

import zio._

trait BoardControl[T] {

  /**
   * Initialize new board with defined size
   */
  def init(size: Int, initMode: InitMode = Shuffled): UIO[Board[T]]

  /**
   * Move tile to zero element
   *
   * @param move direction
   * @return true when move was winning, false - to continue com.madimaxi.puzzle15.game
   */
  def moveTile(move: MoveDirection, board: Board[T]): UIO[Board[T]]

  /**
   * Check winning condition
   *
   * @param ord Correct ordering for given T
   */
  def completed(board: Board[T])(implicit ord: Ordering[T]): UIO[Boolean] = UIO.succeed {
    val boardVal = board.value
    if (boardVal.last.indexOf(Empty) == boardVal.last.size - 1) // if empty tile not in last position - board incomplete
      boardVal.flatten.sortWith {
        case (Value(v1), Value(v2)) => ord.lteq(v1, v2)
        case (Empty, Value(_))      => false
        case _                      => true
      } == boardVal.flatten
    else false
  }
}

object BoardControl extends Accessible[BoardControl[_]]
//{
//  def init[T: Tag](size: Int, initMode: InitMode = Shuffled): ZIO[Has[BoardControl[T]], Nothing, Board[T]] =
//    ZIO.serviceWith[BoardControl[T]](_.init(size, initMode))
//
//  def moveTile[T: Tag](move: MoveDirection, board: Board[T]): ZIO[Has[BoardControl[T]], Nothing, Board[T]] =
//    ZIO.serviceWith[BoardControl[T]](_.moveTile(move, board))
//
//  def completed[T: Tag](board: Board[T])(implicit ord: Ordering[T]): ZIO[Has[BoardControl[T]], Nothing, Boolean] =
//    ZIO.serviceWith[BoardControl[T]](_.completed(board))
//}
