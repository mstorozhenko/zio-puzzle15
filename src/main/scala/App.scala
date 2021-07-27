package com.wix.interview

import game.impl.IntegerBoard

import game.{Board, MoveDown, MoveLeft, MoveRight, MoveUp}

import scala.io.StdIn.readLine

object App {
  def main(args: Array[String]): Unit = {
    val b: Board[Int] = new IntegerBoard(3)
    println(b.board.foreach(t => println(t.mkString(", "))))
    println("--------------------------")
    var isWin = true
    while (isWin) {
      val move = readLine()
      move match {
        case "w" => isWin = !b.moveTile(MoveUp)
        case "s" => isWin = !b.moveTile(MoveDown)
        case "a" => isWin = !b.moveTile(MoveLeft)
        case "d" => isWin = !b.moveTile(MoveRight)
        case _ => println("wrong input")
      }
      println(b.board.foreach(t => println(t.mkString(", "))))
      println("--------------------------")
    }
    println("------------YOU WIN!!!--------------")
  }
}
