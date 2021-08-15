import sbt._

object Dependencies {

  //console
  val jline = "jline" % "jline" % "2.14.6"

  //zio
  val zio = "dev.zio" %% "zio" % "1.0.10"

  //test
  val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9" % "test"
  val mockito = "org.scalatestplus" %% "mockito-3-4" % "3.2.9.0" % "test"
}
