import Dependencies._

ThisBuild / organization := "com.wix"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version := "1.0"

lazy val root = (project in file("."))
  .settings(
    name := "puzzle15",
    libraryDependencies ++= List(
      jline,
      //Test
      scalaTest,
      mockito
    ),
  )