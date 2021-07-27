package com.wix.interview
package game.util

case class ZeroIndex(var i: Int, var j: Int, size: Int) {
  def set(i: Int, j: Int): (Int, Int) = {
    assert(i < size && i >= 0 && j < size && j >= 0, "i, j should be 0 <= (i,j) < size")

    this.i = i
    this.j = j
    (i, j)
  }

  def get: (Int, Int) = (i, j)
}
