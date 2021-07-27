package game

import game.impl.IntegerBoard
import game.util.{Index2D, Random}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.Outcome
import org.scalatest.funsuite.FixtureAnyFunSuite
import org.scalatestplus.mockito.MockitoSugar.mock

class MoveControlTest extends FixtureAnyFunSuite {
  class FixtureParam {
    val size = 4
    val rnd: Random[Int] = mock[Random[Int]]
    when(rnd.shuffle(any)) thenReturn Array.range(0, size * size)

    val testee = new IntegerBoard(size, rnd)
  }

  override protected def withFixture(test: OneArgTest): Outcome = withFixture(test.toNoArgTest(new FixtureParam))

  test("test moveUp") { f =>
    //given
    val zeroElement = f.testee.zeroElementIndex.copy()
    val expected = f.testee.board(zeroElement.i + 1)(zeroElement.j)

    //when
    f.testee.moveTile(MoveUp)
    val actualZeroElement = f.testee.zeroElementIndex.copy()
    val actualBoard = f.testee.board.toList.toArray

    //then
    assert(actualZeroElement != zeroElement)
    assert(actualBoard(zeroElement.i)(zeroElement.j) == expected)
  }

  test("test moveDown") { f =>
    //given
    f.testee.moveTile(MoveUp)

    val zeroElement = f.testee.zeroElementIndex.copy()
    val expected = f.testee.board(zeroElement.i - 1)(zeroElement.j)

    //when
    f.testee.moveTile(MoveDown)
    val actualZeroElement = f.testee.zeroElementIndex.copy()
    val actualBoard = f.testee.board.toList.toArray

    //then
    assert(actualZeroElement != zeroElement)
    assert(actualBoard(zeroElement.i)(zeroElement.j) == expected)
  }

  test("test moveLeft") { f =>
    //given
    val zeroElement = f.testee.zeroElementIndex.copy()
    val expected = f.testee.board(zeroElement.i)(zeroElement.j + 1)

    //when
    f.testee.moveTile(MoveLeft)
    val actualZeroElement = f.testee.zeroElementIndex.copy()
    val actualBoard = f.testee.board.toList.toArray

    //then
    assert(actualZeroElement != zeroElement)
    assert(actualBoard(zeroElement.i)(zeroElement.j) == expected)
  }

  test("test moveRight") { f =>
    //given
    f.testee.moveTile(MoveLeft)

    val zeroElement = f.testee.zeroElementIndex.copy()
    val expected = f.testee.board(zeroElement.i)(zeroElement.j - 1)

    //when
    f.testee.moveTile(MoveRight)
    val actualZeroElement = f.testee.zeroElementIndex.copy()
    val actualBoard = f.testee.board.toList.toArray

    //then
    assert(actualZeroElement != zeroElement)
    assert(actualBoard(zeroElement.i)(zeroElement.j) == expected)
  }

  test("test win") { f =>
    //given
    when(f.rnd.shuffle(any)) thenReturn Array.range(1, f.size * f.size)

    //when
    val testee2 = new IntegerBoard(f.size, f.rnd)
    testee2.zeroElementIndex = Index2D(f.size - 1, f.size - 1, f.size)

    //then
    assert(testee2.checkWin)
  }
}
