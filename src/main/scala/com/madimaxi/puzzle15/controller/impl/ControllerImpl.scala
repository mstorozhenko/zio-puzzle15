package com.madimaxi.puzzle15.controller.impl

import com.madimaxi.puzzle15.console.BoardView
import com.madimaxi.puzzle15.controller.{Continue, Controller, Start, State, Terminate, Win, WrongInput}
import com.madimaxi.puzzle15.game.{Board, BoardControl, MoveDirection, MoveDown, MoveLeft, MoveRight, MoveUp}
import izumi.reflect.Tag
import zio._

case class ControllerImpl[T](control: BoardControl[T], view: BoardView[T]) extends Controller[T] {

  override def processInput(input: Char, s: State)(implicit ord: Ordering[T]): UIO[State] = {
    def game(move: MoveDirection, board: Board[T]): UIO[State] =
      for {
        newBoard <- control.moveTile(move, board)
        isWin    <- control.completed(newBoard)
      } yield if (isWin) Win else Continue(newBoard)

    s match {
      case Win => UIO.succeed(Terminate)
      case Continue(b: Board[T]) =>
        input match {
          case 'w' => game(MoveUp, b)
          case 's' => game(MoveDown, b)
          case 'a' => game(MoveLeft, b)
          case 'd' => game(MoveRight, b)
          case 'q' => UIO.succeed(Terminate)
          case _   => UIO.succeed(WrongInput(b))
        }
      case WrongInput(b) => UIO.succeed(Continue(b))
      case Start =>
        for {
          board <- control.init(input.asDigit)
        } yield Continue(board)
      case Terminate => UIO.dieMessage("The end")
    }
  }

  override def render(s: State): UIO[String] =
    s match {
      case Start =>
        UIO.succeed("""Welcome to puzzle15 game. Use WASD scheme for moving tiles. Type 'q' for quitting game
                      |Please enter board size:
                      |""".stripMargin)
      case Win                       => UIO.succeed("You win!")
      case Continue(board: Board[T]) => view.renderBoard(board).map(_ + "\nPress 'q' to quit")
      case WrongInput(_)             => UIO.succeed("Wrong input. Use WASD controls to move tiles")
      case Terminate                 => UIO.succeed("Exiting...")
    }
}

object ControllerImpl {
  def layer[T: Tag]: URLayer[Has[BoardControl[T]] with Has[BoardView[T]], Has[Controller[T]]] =
    (ControllerImpl[T] _).toLayer
}
