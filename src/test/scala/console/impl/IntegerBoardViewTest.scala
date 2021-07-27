package console.impl

import console.BoardView
import game.Board
import org.mockito.Mockito.when
import org.scalatest.Outcome
import org.scalatest.funsuite.FixtureAnyFunSuite
import org.scalatestplus.mockito.MockitoSugar.mock

class IntegerBoardViewTest extends FixtureAnyFunSuite {

  class FixtureParam {
    val board: Board[Int] = mock[Board[Int]]

    val testee: BoardView[Int] = new IntegerBoardView
  }

  override protected def withFixture(test: OneArgTest): Outcome = withFixture(test.toNoArgTest(new FixtureParam))

  test("board rendered with input array") { f =>
    //given
    val expected = "\n\u001B[36m[  1] \u001B[36m[  2] \u001B[36m[  3] \u001B[36m[  4] \u001B[0m\n\u001B[36m[  5] \u001B[36m[  6] \u001B[36m[  7] \u001B[36m[  8] \u001B[0m\n\u001B[36m[  9] \u001B[36m[ 10] \u001B[36m[ 11] \u001B[36m[ 12] \u001B[0m\n\u001B[36m[ 13] \u001B[36m[ 14] \u001B[36m[ 15] \u001B[31m[   ] \u001B[0m"

    when(f.board.board) thenReturn Array(Array(1, 2, 3, 4), Array(5, 6, 7, 8), Array(9, 10, 11, 12), Array(13, 14, 15, 0))
    when(f.board.size) thenReturn 4

    //when
    val actual = f.testee.renderBoard(f.board)

    //then
    assert(expected == actual)
  }

  test("board rendered with empty array") { f =>
    //given
    when(f.board.size) thenReturn 0
    when(f.board.board) thenReturn Array(Array())

    //when
    val actual = f.testee.renderBoard(f.board)

    //then
    assert("" == actual)
  }
}
