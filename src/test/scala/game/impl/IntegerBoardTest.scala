package game.impl

import game.util.Random
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.Outcome
import org.scalatest.funsuite.FixtureAnyFunSuite
import org.scalatestplus.mockito.MockitoSugar.mock

class IntegerBoardTest extends FixtureAnyFunSuite {

  class FixtureParam {
    val size = 4

    val testee = new IntegerBoard(size)
  }

  override protected def withFixture(test: OneArgTest): Outcome = withFixture(test.toNoArgTest(new FixtureParam))

  test("board generated with correct numbers") { f =>
    //given

    //when
    val actual = f.testee.board

    //then
    assert(actual.flatten.sorted sameElements Array.range(0, f.size * f.size))
  }

  test("board generated and shuffled") { f =>
    //given

    //when
    val actual = f.testee.board

    //then
    assert(!actual.flatten.sameElements(Array.range(0, f.size * f.size)))
  }

  test("board balanced return false for shuffled board") { f =>
    //given

    //when
    val actual = f.testee.balanced

    //then
    assert(!actual)
  }

  test("board balanced return true for complete board") { f =>
    //given
    val rnd = mock[Random[Int]]
    when(rnd.shuffle(any)) thenReturn Array.range(1, f.size * f.size)


    val testee2 = new IntegerBoard(f.size, rnd)

    //when
    val actual = testee2.balanced

    //then
    assert(actual)
  }
}
