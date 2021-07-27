package game

sealed trait MoveDirection

case object MoveUp extends MoveDirection

case object MoveDown extends MoveDirection

case object MoveLeft extends MoveDirection

case object MoveRight extends MoveDirection
