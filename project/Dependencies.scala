import sbt._

object Dependencies {
  val jline = "jline" % "jline" % "2.14.6"
  val zio = "dev.zio" %% "zio" % "1.0.10"
  val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9" % "test"
  val mockito = "org.scalatestplus" %% "mockito-3-4" % "3.2.9.0" % "test"
}
