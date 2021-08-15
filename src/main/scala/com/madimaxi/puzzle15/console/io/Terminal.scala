package com.madimaxi.puzzle15.console.io

import zio._

trait Terminal {
  def read(): UIO[Char]

  def print(s: String): UIO[Unit]

  def clearScreen(): UIO[Unit]
}

object Terminal {
  def read(): ZIO[Has[Terminal], Throwable, Char] = ZIO.serviceWith[Terminal](_.read())

  def print(s: String): ZIO[Has[Terminal], Throwable, Unit] = ZIO.serviceWith[Terminal](_.print(s))

  def clearScreen(): ZIO[Has[Terminal], Throwable, Unit] = ZIO.serviceWith[Terminal](_.clearScreen())
}
