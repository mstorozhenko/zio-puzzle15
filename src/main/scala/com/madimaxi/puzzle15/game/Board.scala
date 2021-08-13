package com.madimaxi.puzzle15.game

sealed trait Field[+T]

case object Empty extends Field[Nothing]

case class Value[+T](value: T) extends Field[T]

case class Board[+T] private[game](value: Seq[Seq[Field[T]]], emptyIndex: (Int, Int))
