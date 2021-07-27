package console.io

import jline.console.ConsoleReader

class ConsoleIO extends IO {
  override def read(): Char = {
    val console = new ConsoleReader()
    val l = console.readCharacter().toChar
    console.close()
    l
  }

  override def print(s: String): Unit = println(s)

  override def clearScreen(): Unit = println("\u001b[H\u001b[2J")
}
