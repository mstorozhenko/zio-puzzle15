package com.madimaxi.puzzle15.controller

import com.madimaxi.puzzle15.game.Board

sealed trait State

case class Continue[+T](board: Board[T]) extends State

case class WrongInput[+T](board: Board[T]) extends State

case object Start extends State

case object Terminate extends State

case object Win extends State

