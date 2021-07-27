package controller.impl

import console.BoardView
import console.io.IO
import controller.{Continue, Start, Terminate, WrongInput}
import game.Board
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{times, verify, verifyNoInteractions, when}
import org.scalatest.Outcome
import org.scalatest.funsuite.FixtureAnyFunSuite
import org.scalatestplus.mockito.MockitoSugar.mock

class GameControllerTest extends FixtureAnyFunSuite {

  class FixtureParam {
    val io: IO = mock[IO]
    val board: Board[Int] = mock[Board[Int]]
    val view: BoardView[Int] = mock[BoardView[Int]]

    val testee = new GameController[Int](board, view, io)
  }

  override protected def withFixture(test: OneArgTest): Outcome = withFixture(test.toNoArgTest(new FixtureParam))


  test("processInput should respond with correct mode on move commands") { f =>
    //given
    when(f.board.moveTile(any)) thenReturn false

    //when
    val w = f.testee.processInput('w')
    val a = f.testee.processInput('a')
    val s = f.testee.processInput('s')
    val d = f.testee.processInput('d')

    //then
    assert(w == Continue)
    assert(a == Continue)
    assert(s == Continue)
    assert(d == Continue)
  }

  test("processInput should return Terminate mode when 'q' entered") { f =>
    //given

    //when
    val q = f.testee.processInput('q')

    //then
    assert(q == Terminate)
  }

  test("processInput should return WrongInput mode when user pasted unexpected command") { f =>
    //given

    //when
    val g = f.testee.processInput('g')

    //then
    assert(g == WrongInput)
  }

  test("render should call view render function") { f =>
    //given

    //when
    f.testee.render()

    //then
    verify(f.view).renderBoard(f.board)
  }

  test("gameLoop should terminate") { f =>
    //given

    //when
    f.testee.gameLoop(Terminate)

    //then
    verifyNoInteractions(f.board, f.view, f.io)
  }

  test("gameLoop should continue") { f =>
    //given
    when(f.view.renderBoard(any())) thenReturn "1"
    when(f.io.read()) thenReturn 'q'

    //when
    f.testee.gameLoop(Continue)

    //then
    verify(f.io, times(1)).print("1\nType 'q' to quit")
    verify(f.io, times(1)).read()
    verify(f.io, times(1)).clearScreen()
  }

  test("gameLoop should react on WrongInput and then go to Continue") { f =>
    //given
    when(f.view.renderBoard(any())) thenReturn "1"
    when(f.io.read()) thenReturn 'q'

    //when
    f.testee.gameLoop(WrongInput)

    //then
    verify(f.io, times(2)).clearScreen()
    verify(f.io, times(1)).print("Wrong input. User WASD controls to move tiles")
    verify(f.io, times(1)).print("1\nType 'q' to quit")
  }

  test("gameLoop should react on Start and then go to Continue") { f =>
    //given
    when(f.view.renderBoard(any())) thenReturn "1"
    when(f.io.read()) thenReturn 'q'

    //when
    f.testee.gameLoop(Start)

    //then
    verify(f.io, times(2)).clearScreen()
    verify(f.io, times(1)).print("Welcome to puzzle15 game. Use WASD scheme for moving tiles. Type 'q' for quitting game")
    verify(f.io, times(1)).print("1\nType 'q' to quit")
  }
}
