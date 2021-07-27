package controller.impl

import console.BoardView
import console.io.IO
import controller.{Continue, Controller, Mode, Start, Terminate, Win, WrongInput}
import game.{Board, MoveDown, MoveLeft, MoveRight, MoveUp}

class GameController[T](board: Board[T], view: BoardView[T], io: IO) extends Controller {

  override def processInput(s: Char): Mode = s match {
    case 'w' => if (board.moveTile(MoveUp)) Win else Continue
    case 's' => if (board.moveTile(MoveDown)) Win else Continue
    case 'a' => if (board.moveTile(MoveLeft)) Win else Continue
    case 'd' => if (board.moveTile(MoveRight)) Win else Continue
    case 'q' => Terminate
    case _ => WrongInput
  }

  override def render(): String = view.renderBoard(board) + "\nType 'q' to quit"

  override def gameLoop(m: Mode): Unit = {
    m match {
      case Win => io.print("You win!")
      case Continue =>
        io.print(render())
        val nextMode = processInput(io.read())
        io.clearScreen()
        gameLoop(nextMode)
      case WrongInput =>
        io.clearScreen()
        io.print("Wrong input. User WASD controls to move tiles")
        gameLoop(Continue)
      case Start =>
        io.clearScreen()
        io.print("Welcome to puzzle15 game. Use WASD scheme for moving tiles. Type 'q' for quitting game")
        gameLoop(Continue)
      case Terminate =>
    }
  }
}
