package com.madimaxi.puzzle15.game.impl

import com.madimaxi.puzzle15.game._
import zio.{Has, Task, URLayer}
import zio.random.Random

case class IntBoardControl(rnd: Random.Service) extends BoardControl[Int] {

  /**
   * Initialize new board with defined size
   */
  override def init(size: Int, initMode: InitMode = Shuffled): Task[Board[Int]] = for {
    shuffled <- initMode match {
      case Shuffled => rnd.shuffle((0 until (size * size)).toList)
      case Natural => Task.succeed(0 until (size * size))
    }
    board: Seq[Seq[Field[Int]]] <- Task.succeed(
      shuffled
        .sliding(size, size)
        .toSeq
        .map(_.map(t => if (t == 0) Empty else Value(t)))
    )
    emptyIndex <- {
      val row = board.indexWhere(_.contains(Empty))
      val cell = if (row > -1) board(row).indexOf(Empty) else -1
      if (row == -1 || cell == -1)
        Task.fail(new RuntimeException("Element not found"))
      else Task.succeed((row, cell))
    }
  } yield Board(board, emptyIndex)

  /**
   * Move tile to zero element
   *
   * @param move direction
   * @return true when move was winning, false - to continue com.madimaxi.puzzle15.game
   */
  override def moveTile(move: MoveDirection, board: Board[Int]): Task[Board[Int]] =
    move match {
      case MoveUp =>
        swap(board, (board.emptyIndex._1 + 1, board.emptyIndex._2), board.emptyIndex)
      case MoveDown =>
        swap(board, (board.emptyIndex._1 - 1, board.emptyIndex._2), board.emptyIndex)
      case MoveLeft =>
        swap(board, (board.emptyIndex._1, board.emptyIndex._2 + 1), board.emptyIndex)
      case MoveRight =>
        swap(board, (board.emptyIndex._1, board.emptyIndex._2 - 1), board.emptyIndex)
    }

  /**
   * swap elements in board and return new board
   *
   * @param board - flatten board
   * @param from  - moving element index
   * @param to    - Empty element index
   * @return new board with swapped elements
   */
  private def swap(board: Board[Int], from: (Int, Int), to: (Int, Int)): Task[Board[Int]] = Task.succeed {
    val size = board.value.size
    if (from._1 < size && from._1 >= 0 && from._2 < size && from._2 >= 0) {
      val flatten: Seq[Field[Int]] = board.value.flatten
      Board(flatten
        .updated(to._1 * size + to._2, flatten(from._1 * size + from._2))
        .updated(from._1 * size + from._2, Empty)
        .sliding(size, size)
        .toSeq, from)
    } else board
  }
}

object IntBoardControl {
  val layer: URLayer[Has[Random.Service], Has[BoardControl[Int]]] = (IntBoardControl(_)).toLayer
}
