package com.wix.interview

import game.impl.IntegerBoard
import game.Board
import console.impl.IntegerBoardView
import console.BoardView
import controller.Continue
import controller.impl.GameController

import com.wix.interview.console.io.{ConsoleIO, IO}

object App {

  def main(args: Array[String]): Unit = {
    val board: Board[Int] = new IntegerBoard(4)
    val view: BoardView[Int] = new IntegerBoardView
    val consoleIO: IO = new ConsoleIO
    val controller = new GameController(board, view, consoleIO)

    controller.gameLoop(Continue)
  }
}
