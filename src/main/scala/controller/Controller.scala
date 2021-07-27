package controller

trait Controller {
  def processInput(s: Char): Mode
  def render(): String
  def gameLoop(m: Mode): Unit
}
