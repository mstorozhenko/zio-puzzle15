package com.wix.interview
package console

import game.Board

trait BoardView {
  def drawBoard(board: Board[Int]): String
}
