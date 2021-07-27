package console.impl

import console.BoardView
import game.Board
import game.util.Properties.minBoardSize

class IntegerBoardView extends BoardView[Int] {
  private val cellLength = 3

  def renderBoard(b: Board[Int]): String = {
    if (b.size < minBoardSize) ""
    else
      s"""
         |${
        b.board.map(t =>
          t.map(e =>
            if (e == b.zero) String.format(s"\u001B[31m[%${cellLength}s] ", " ")
            else String.format(s"\u001B[36m[%${cellLength}d] ", e)).mkString)
          .mkString("\u001B[0m\n")
      }\u001B[0m"""
        .stripMargin
  }
}
