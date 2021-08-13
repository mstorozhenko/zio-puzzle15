package com.madimaxi.puzzle15.controller.impl

import com.madimaxi.puzzle15.console.BoardView
import com.madimaxi.puzzle15.controller.{Continue, Controller, Start, State, Terminate, Win, WrongInput}
import com.madimaxi.puzzle15.game.{Board, BoardControl, MoveDirection, MoveDown, MoveLeft, MoveRight, MoveUp}
import izumi.reflect.Tag
import zio.{Function2ToLayerSyntax, Has, Task, URLayer, ZIO}

case class GameController[T](control: BoardControl[T], view: BoardView[T]) extends Controller[T] {

  override def processInput(input: Char, s: State)(implicit ord: Ordering[T]): Task[State] = {
    def game(move: MoveDirection, board: Board[T]): Task[State] = {
      for {
        newBoard <- control.moveTile(move, board)
        isWin <- control.completed(newBoard)
      } yield if (isWin) Win else Continue(newBoard)
    }

    s match {
      case Win => Task.succeed(Terminate)
      case Continue(b: Board[T]) =>
        input match {
          case 'w' => game(MoveUp, b)
          case 's' => game(MoveDown, b)
          case 'a' => game(MoveLeft, b)
          case 'd' => game(MoveRight, b)
          case 'q' => Task.succeed(Terminate)
          case _ => Task.succeed(WrongInput(b))
        }
      case WrongInput(b) => Task.succeed(Continue(b))
      case Start => for {
        board <- control.init(input.asDigit)
      } yield Continue(board)
      case Terminate => Task.fail(new RuntimeException("The end")) //TODO
    }
  }

  override def render(s: State): Task[String] =
    s match {
      case Start => Task.succeed(
        """Welcome to puzzle15 game. Use WASD scheme for moving tiles. Type 'q' for quitting com.madimaxi.puzzle15.game
          |Please enter board size:
          |""".stripMargin)
      case Win => Task.succeed("You win!")
      case Continue(board: Board[T]) => view.renderBoard(board).map(_ + "\nPress 'q' to quit")
      case WrongInput(_) => Task.succeed("Wrong input. Use WASD controls to move tiles")
      case Terminate => Task.succeed("Exiting...")
    }
}

object GameController {
  def layer[T: Tag]: URLayer[Has[BoardControl[T]] with Has[BoardView[T]], Has[Controller[T]]] =
    (GameController[T] _).toLayer
}
