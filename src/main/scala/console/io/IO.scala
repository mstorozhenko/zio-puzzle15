package com.wix.interview
package console.io

trait IO {
  def read(): Char

  def print(s: String): Unit

  def clearScreen(): Unit
}
