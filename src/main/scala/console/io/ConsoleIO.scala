package com.wix.interview
package console.io

import jline.console.ConsoleReader

class ConsoleIO extends IO {
  override def readChar(): Char = {
    val console = new ConsoleReader()
    val l = console.readCharacter().toChar
    console.close()
    l
  }

  override def printLine(s: String): Unit = println(s)

  override def clearScreen(): Unit = println("\u001b[H\u001b[2J")
}
