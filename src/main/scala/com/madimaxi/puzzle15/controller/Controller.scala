package com.madimaxi.puzzle15.controller

import izumi.reflect.Tag
import zio._

trait Controller[T] {
  def processInput(input: Char, s: State)(implicit ord: Ordering[T]): UIO[State]

  def render(s: State): UIO[String]
}

object Controller {
  def processInput[T: Tag](input: Char, s: State)(implicit ord: Ordering[T]): ZIO[Has[Controller[T]], Nothing, State] =
    ZIO.serviceWith[Controller[T]](_.processInput(input, s))

  def render[T: Tag](s: State): ZIO[Has[Controller[T]], Nothing, String] = ZIO.serviceWith[Controller[T]](_.render(s))
}
