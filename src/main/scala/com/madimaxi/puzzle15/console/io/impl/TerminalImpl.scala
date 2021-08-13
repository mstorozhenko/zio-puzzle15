package com.madimaxi.puzzle15.console.io.impl

import com.madimaxi.puzzle15.console.io.Terminal
import jline.console.ConsoleReader
import zio.console.Console
import zio.{Has, Task, ULayer, URLayer, ZLayer}

case class TerminalImpl(console: Console.Service) extends Terminal {
  override def read(): Task[Char] = Task.effectTotal {
    val console = new ConsoleReader()
    val l = console.readCharacter().toChar
    console.close()
    l
  }


  override def print(s: String): Task[Unit] =
    console.putStrLn(s)

  override def clearScreen(): Task[Unit] =
    console.putStr("\u001b[H\u001b[2J")
}

object TerminalImpl {
  val layer: URLayer[Has[Console.Service], Has[Terminal]] = (TerminalImpl(_)).toLayer
}
