package com.wix.interview
package controller.impl

import controller.{Continue, Controller, Mode, Terminate, Win}
import game.{Board, MoveDown, MoveLeft, MoveRight, MoveUp}
import console.BoardView

import console.io.IO

class GameController[T](board: Board[T], view: BoardView[T], io: IO) extends Controller {

  override def processInput(s: Char): Mode = s match {
    case "w" => if (board.moveTile(MoveUp)) Win else Continue
    case "s" => if (board.moveTile(MoveDown)) Win else Continue
    case "a" => if (board.moveTile(MoveLeft)) Win else Continue
    case "d" => if (board.moveTile(MoveRight)) Win else Continue
    case "q" => Terminate
    case _ => Continue
  }

  override def render(): String = view.renderBoard(board)

  override def gameLoop(m: Mode): Unit = {
    m match {
      case Terminate =>
      case Win => io.printLine("You win!")
      case Continue =>
        io.clearScreen()
        io.printLine(render())
        val nextMode = processInput(io.readChar())
        gameLoop(nextMode)
    }
  }
}
