package game

trait MoveControl[T] {
  self: Board[T] =>

  /**
   * Move tile to zero element
   *
   * @param move direction
   * @return true when move was winning, false - to continue game
   */
  def moveTile(move: MoveDirection): Boolean = {

    def swap(i: Int, j: Int): Unit = {
      if (i < size && i >= 0 && j < size && j >= 0) {
        val toSwap = board(i)(j)
        board(i)(j) = zero
        board(zeroElementIndex.i)(zeroElementIndex.j) = toSwap
        zeroElementIndex.set(i, j)
      }
    }

    move match {
      case MoveUp =>
        swap(zeroElementIndex.i + 1, zeroElementIndex.j)
        checkWin()
      case MoveDown =>
        swap(zeroElementIndex.i - 1, zeroElementIndex.j)
        checkWin()
      case MoveLeft =>
        swap(zeroElementIndex.i, zeroElementIndex.j + 1)
        checkWin()
      case MoveRight =>
        swap(zeroElementIndex.i, zeroElementIndex.j - 1)
        checkWin()
    }
  }

  def checkWin(): Boolean = {
    (zeroElementIndex.i == size - 1 && zeroElementIndex.j == size - 1) && balanced
  }

}
