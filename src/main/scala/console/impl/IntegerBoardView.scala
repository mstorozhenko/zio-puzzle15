package com.wix.interview
package console.impl

import console.BoardView
import game.Board


class IntegerBoardView extends BoardView {
  val cellLength = 3

  def drawBoard(b: Board[Int]): String = {
    s"""
       |${
      b.board.map(t =>
        t.map(e =>
          if (e == b.zero) String.format(s"\u001B[31m[%${cellLength}s] ", " ")
          else String.format(s"\u001B[36m[%${cellLength}d] ", e)).mkString)
        .mkString("\u001B[0m\n")
    }\u001B[0m
       |Type 'q' to quit"""
      .stripMargin
  }
}
