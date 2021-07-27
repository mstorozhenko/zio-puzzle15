package com.wix.interview

import game.impl.IntegerBoard
import game.{Board, MoveDown, MoveLeft, MoveRight, MoveUp}

import com.wix.interview.console.impl.IntegerBoardView

import scala.io.StdIn.readLine

object App {
  val ANSI_CLEARSCREEN: String =
    "\u001b[H\u001b[2J"


  def main(args: Array[String]): Unit = {
    val b: Board[Int] = new IntegerBoard(4)
    val view = new IntegerBoardView

    print(ANSI_CLEARSCREEN)
    println(view.drawBoard(b))

    var isWin = true
    while (isWin) {
      val move = readLine()
      move match {
        case "w" => isWin = !b.moveTile(MoveUp)
        case "s" => isWin = !b.moveTile(MoveDown)
        case "a" => isWin = !b.moveTile(MoveLeft)
        case "d" => isWin = !b.moveTile(MoveRight)
        case "q" => isWin = false
        case _ => println("wrong input")
      }
      print(ANSI_CLEARSCREEN)
      println(view.drawBoard(b))
    }
    println("------------YOU WIN!!!--------------")

    //print("\\u001b[H\\u001b[2J")
  }
}
