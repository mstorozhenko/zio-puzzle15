package com.madimaxi.puzzle15.console

import com.madimaxi.puzzle15.game.Board
import izumi.reflect.Tag
import zio.{Has, Task, ZIO}

trait BoardView[T] {
  def renderBoard(board: Board[T]): Task[String]
}

object BoardView {
  def renderBoard[T: Tag](board: Board[T]): ZIO[Has[BoardView[T]], Throwable, String] =
    ZIO.serviceWith[BoardView[T]](_.renderBoard(board))
}
