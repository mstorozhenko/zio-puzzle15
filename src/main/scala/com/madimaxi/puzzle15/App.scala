package com.madimaxi.puzzle15

import com.madimaxi.puzzle15.console.impl.IntBoardView
import com.madimaxi.puzzle15.console.io.impl.TerminalImpl
import com.madimaxi.puzzle15.controller.impl.GameController
import com.madimaxi.puzzle15.controller.loop.Runner
import com.madimaxi.puzzle15.controller.loop.impl.RunnerImpl
import com.madimaxi.puzzle15.controller.{Start, State}
import com.madimaxi.puzzle15.game.impl.IntBoardControl
import zio.{ExitCode, Has, Task, URIO, ZIO}

object App extends zio.App {

  val program: ZIO[Has[Runner[Int]], Throwable, Any] = {
    def loop(state: State): ZIO[Has[Runner[Int]], Throwable, Any] =
      Runner.start[Int](state).foldM(
        _ => Task.unit,
        r => loop(r)
      )

    loop(Start)
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    program.provideLayer(
      ((IntBoardControl.layer ++ IntBoardView.layer >>> GameController.layer[Int]) ++ TerminalImpl.layer)
        >>> RunnerImpl.layer[Int]
    ).exitCode
  }
}
