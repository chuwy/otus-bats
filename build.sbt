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

