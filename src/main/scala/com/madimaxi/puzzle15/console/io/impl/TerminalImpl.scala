package com.madimaxi.puzzle15.console.io.impl

import com.madimaxi.puzzle15.console.io.Terminal
import jline.console.ConsoleReader
import zio.console.Console
import zio._

case class TerminalImpl(console: Console.Service) extends Terminal {
  override def read(): UIO[Char] =
  //standard zio have only readLine, jline can readCharacter without pressing enter
    ZManaged.makeEffect(new ConsoleReader())(t => t.close()).use(
      reader => Task.succeed(reader.readCharacter().toChar)
    ).orDie


  override def print(s: String): UIO[Unit] = console.putStrLn(s).orDie

  override def clearScreen(): UIO[Unit] = console.putStr("\u001b[H\u001b[2J").orDie
}

object TerminalImpl {
  val layer: URLayer[Has[Console.Service], Has[Terminal]] = (TerminalImpl(_)).toLayer
}
