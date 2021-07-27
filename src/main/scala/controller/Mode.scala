package controller

sealed trait Mode
case object Continue extends Mode
case object Terminate extends Mode
case object Win extends Mode
case object WrongInput extends Mode
case object Start extends Mode
