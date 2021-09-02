import sbt._

object Dependencies {

  object V {
    // Scala
    val circe            = "0.13.0"
    val http4s           = "0.21.13"
    // Scala (test only)
    val specs2           = "4.10.5"
    val scalaCheck       = "1.15.1"
  }

  // Scala
  val all = List(
    "io.circe"                   %% "circe-parser"         % V.circe,
    "org.http4s"                 %% "http4s-dsl"           % V.http4s,

    "org.specs2"                 %% "specs2-core"          % V.specs2         % Test,
    "org.specs2"                 %% "specs2-scalacheck"    % V.specs2         % Test,
    "org.scalacheck"             %% "scalacheck"           % V.scalaCheck     % Test
  )
}
