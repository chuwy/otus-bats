lazy val root = project.in(file("."))
  .settings(
    name := "otusbats",
    version := "0.1.0-rc1",
    organization := "me.chuwy",
    scalaVersion := "2.13.5"
  )
  .settings(BuildSettings.buildSettings)
  .settings(BuildSettings.scalifySettings)
  .settings(libraryDependencies ++= Dependencies.all)
  .settings(BuildSettings.helpersSettings)

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)

libraryDependencies += "org.scalatest"  %% "scalatest"    % "3.2.10" % "test"
libraryDependencies += "org.mockito"    % "mockito-core"  % "3.0.0" % "test"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.13.3"