package com.madimaxi.puzzle15.controller.loop

import com.madimaxi.puzzle15.controller.State
import izumi.reflect.Tag
import zio.{Has, Task, ZIO}

trait Runner[T] {
  def start(s: State)(implicit ordering: Ordering[T]): Task[State]
}

object Runner {
  def start[T: Tag](s: State)(implicit ordering: Ordering[T]): ZIO[Has[Runner[T]], Throwable, State] =
    ZIO.serviceWith[Runner[T]](_.start(s))
}
