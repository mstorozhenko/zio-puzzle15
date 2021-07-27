package com.wix.interview
package console.io

trait IO {
  def readChar(): Char

  def printLine(s: String): Unit

  def clearScreen(): Unit
}
