package com.madimaxi.puzzle15.controller

import izumi.reflect.Tag
import zio.{Has, Task, ZIO}

trait Controller[T] {
  def processInput(input: Char, s: State)(implicit ord: Ordering[T]): Task[State]

  def render(s: State): Task[String]
}

object Controller {
  def processInput[T: Tag](input: Char, s: State)(implicit ord: Ordering[T]): ZIO[Has[Controller[T]], Throwable, State] =
    ZIO.serviceWith[Controller[T]](_.processInput(input, s))

  def render[T: Tag](s: State): ZIO[Has[Controller[T]], Throwable, String] = ZIO.serviceWith[Controller[T]](_.render(s))
}
