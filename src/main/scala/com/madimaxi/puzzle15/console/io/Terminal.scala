package com.madimaxi.puzzle15.console.io

import zio.{Has, Task, ZIO}

trait Terminal {
  def read(): Task[Char]

  def print(s: String): Task[Unit]

  def clearScreen(): Task[Unit]
}

object Terminal {
  def read(): ZIO[Has[Terminal], Throwable, Char] = ZIO.serviceWith[Terminal](_.read())

  def print(s: String): ZIO[Has[Terminal], Throwable, Unit] = ZIO.serviceWith[Terminal](_.print(s))

  def clearScreen(): ZIO[Has[Terminal], Throwable, Unit] = ZIO.serviceWith[Terminal](_.clearScreen())
}
