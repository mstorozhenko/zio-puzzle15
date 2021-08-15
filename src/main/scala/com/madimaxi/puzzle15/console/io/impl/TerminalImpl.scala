package com.madimaxi.puzzle15.console.io.impl

import com.madimaxi.puzzle15.console.io.Terminal
import jline.console.ConsoleReader
import zio.console.Console
import zio.{Has, Task, URLayer, ZManaged}

case class TerminalImpl(console: Console.Service) extends Terminal {
  override def read(): Task[Char] =
  //standard zio have only readLine, jline can readCharacter without pressing enter
    ZManaged.makeEffect(new ConsoleReader())(t => t.close()).use(
      reader => Task.succeed(reader.readCharacter().toChar)
    )


  override def print(s: String): Task[Unit] =
    console.putStrLn(s)

  override def clearScreen(): Task[Unit] =
    console.putStr("\u001b[H\u001b[2J")
}

object TerminalImpl {
  val layer: URLayer[Has[Console.Service], Has[Terminal]] = (TerminalImpl(_)).toLayer
}
