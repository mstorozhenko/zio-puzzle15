package console

import game.Board

trait BoardView[T] {
  def renderBoard(board: Board[T]): String
}
