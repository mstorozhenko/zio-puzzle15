package com.madimaxi.puzzle15.console

import com.madimaxi.puzzle15.game.Board
import zio._

trait BoardView[T] {
  def renderBoard(board: Board[T]): UIO[String]
}

object BoardView extends Accessible[BoardView[_]]
//{
//  def renderBoard[T: Tag](board: Board[T]): ZIO[Has[BoardView[T]], Nothing, String] =
//    ZIO.serviceWith[BoardView[T]](_.renderBoard(board))
//}
