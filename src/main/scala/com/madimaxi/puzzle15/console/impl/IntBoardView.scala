package com.madimaxi.puzzle15.console.impl

import com.madimaxi.puzzle15.console.BoardView
import com.madimaxi.puzzle15.game.{Board, Empty, Value}
import zio._

case class IntBoardView() extends BoardView[Int] {
  private val cellLength = 3

  def renderBoard(b: Board[Int]): UIO[String] = UIO.succeed(
    s"""
       |${b.value
      .map(t =>
        t.map {
          case Empty    => String.format(s"${Console.RED}[%${cellLength}s] ", " ")
          case Value(e) => String.format(s"${Console.CYAN}[%${cellLength}d] ", e)
        }.mkString
      )
      .mkString(s"${Console.RESET}\n")}${Console.RESET}""".stripMargin
  )
}

object IntBoardView {
  val layer: ULayer[Has[BoardView[Int]]] = ZLayer.succeed(IntBoardView())
}
