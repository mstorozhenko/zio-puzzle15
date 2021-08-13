package com.madimaxi.puzzle15.game

sealed trait InitMode

case object Shuffled extends InitMode

case object Natural extends InitMode
