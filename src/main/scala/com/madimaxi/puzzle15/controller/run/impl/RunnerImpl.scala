package com.madimaxi.puzzle15.controller.run.impl

import com.madimaxi.puzzle15.console.io.Terminal
import com.madimaxi.puzzle15.controller.{Controller, State, Terminate}
import com.madimaxi.puzzle15.controller.run.Runner
import izumi.reflect.Tag
import zio.{Function2ToLayerSyntax, Has, Task, URLayer}

case class RunnerImpl[T](controller: Controller[T], console: Terminal) extends Runner[T] {

  override def start(s: State)(implicit ordering: Ordering[T]): Task[State] = for {
    _ <- console.clearScreen()
    frame <- controller.render(s)
    _ <- console.print(frame)
    input <- if (s == Terminate) Task('q') else console.read()
    nextState <- controller.processInput(input, s)
  } yield nextState
}

object RunnerImpl {
  def layer[T: Tag]: URLayer[Has[Controller[T]] with Has[Terminal], Has[Runner[T]]] = (RunnerImpl[T] _).toLayer
}
