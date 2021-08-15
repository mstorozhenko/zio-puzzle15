package com.madimaxi.puzzle15.controller.run

import com.madimaxi.puzzle15.controller.State
import izumi.reflect.Tag
import zio._

trait Runner[T] {
  def start(s: State)(implicit ordering: Ordering[T]): IO[Unit, State]
}

object Runner {
  def start[T: Tag](s: State)(implicit ordering: Ordering[T]): ZIO[Has[Runner[T]], Unit, State] =
    ZIO.serviceWith[Runner[T]](_.start(s))
}
