package com.madimaxi.puzzle15

import com.madimaxi.puzzle15.console.impl.IntBoardView
import com.madimaxi.puzzle15.console.io.impl.TerminalImpl
import com.madimaxi.puzzle15.controller.impl.ControllerImpl
import com.madimaxi.puzzle15.controller.run.Runner
import com.madimaxi.puzzle15.controller.run.impl.RunnerImpl
import com.madimaxi.puzzle15.controller.{Start, State}
import com.madimaxi.puzzle15.game.impl.IntBoardControl
import zio._

object App extends zio.App {

  val program: ZIO[Has[Runner[Int]], Throwable, Any] = {
    def loop(state: State): ZIO[Has[Runner[Int]], Throwable, Any] =
      Runner.start[Int](state).foldM(
        _ => UIO.unit,
        r => loop(r)
      )

    loop(Start)
  }


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    program.provideLayer(
      ((IntBoardControl.layer ++ IntBoardView.layer >>> ControllerImpl.layer[Int]) ++ TerminalImpl.layer)
        >>> RunnerImpl.layer[Int]
    ).exitCode
  }
}
