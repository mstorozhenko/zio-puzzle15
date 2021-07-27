package com.wix.interview
package game.impl

import game.Board

import game.util.ZeroIndex

import scala.annotation.tailrec
import scala.util.Random

class IntegerBoard(val size: Int) extends Board[Int] {

  // this grid size just comes from top of my head, grid 20x20 seams too big for any 'standard' player
  assert(size > 2 && size < 20, "Grid size should be 1 < x < 20")

  override val zero: Int = 0
  override var zeroElementIndex: ZeroIndex = ZeroIndex(0, 0, size)

  val board: Array[Array[Int]] = {
    val r = Random.shuffle(Array.range(0, size * size))

    @tailrec
    def fillBoard(s: Array[Int], acc: Array[Array[Int]], i: Int): Array[Array[Int]] = {
      s match {
        case Array() => acc
        case a =>
          val (take, drop) = a.splitAt(size)
          acc(i) = take
          val indexOfZero = take.indexOf(0)
          if (indexOfZero != -1) zeroElementIndex.set(i, indexOfZero)
          fillBoard(drop, acc, i + 1)
      }
    }

    fillBoard(r.toArray, Array.ofDim[Int](size, size), 0)
  }

  override def balanced: Boolean = board.flatten.take(size * size - 1) sameElements Array.range(1, size * size)
}
