package game.impl

import game.Board
import game.util.{Index2D, Random}
import game.util.Properties.{maxBoardSize, minBoardSize}

import scala.annotation.tailrec

class IntegerBoard(val size: Int, rnd: Random[Int] = Random[Int]) extends Board[Int] {

  assert(size > minBoardSize && size < maxBoardSize, s"Grid size should be $minBoardSize < x < $maxBoardSize")

  override val zero: Int = 0
  override var zeroElementIndex: Index2D = Index2D(0, 0, size)

  val board: Array[Array[Int]] = {
    val r = rnd.shuffle(Array.range(0, size * size))

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

    fillBoard(r, Array.ofDim[Int](size, size), 0)
  }

  override def balanced: Boolean = board.flatten.take(size * size - 1) sameElements Array.range(1, size * size)
}
