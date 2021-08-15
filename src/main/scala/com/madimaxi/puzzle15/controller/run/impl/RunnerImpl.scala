package com.madimaxi.puzzle15.controller.run.impl

import com.madimaxi.puzzle15.console.io.Terminal
import com.madimaxi.puzzle15.controller.{Controller, State, Terminate}
import com.madimaxi.puzzle15.controller.run.Runner
import izumi.reflect.Tag
import zio._

case class RunnerImpl[T](controller: Controller[T], terminal: Terminal) extends Runner[T] {

  override def start(s: State)(implicit ordering: Ordering[T]): IO[Unit, State] = for {
    _         <- terminal.clearScreen()
    frame     <- controller.render(s)
    _         <- terminal.print(frame)
    input     <- if (s == Terminate) IO.fail() else terminal.read()
    nextState <- controller.processInput(input, s)
  } yield nextState
}

object RunnerImpl {
  def layer[T: Tag]: URLayer[Has[Controller[T]] with Has[Terminal], Has[Runner[T]]] = (RunnerImpl[T] _).toLayer
}
