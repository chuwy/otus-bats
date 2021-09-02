// SBT
import sbt._
import Keys._

/**
 * To enable any of these you need to explicitly add Settings value to build.sbt
 */
object BuildSettings {

  // Makes package (build) metadata available withing source code
  lazy val scalifySettings = Seq(
    sourceGenerators in Compile += Def.task {
      val file = (sourceManaged in Compile).value / "settings.scala"
    IO.write(file, """package me.chuwy.otusbats.generated
                      |object ProjectMetadata {
                      |  val version = "%s"
                      |  val name = "%s"
                      |  val organization = "%s"
                      |  val scalaVersion = "%s"
                      |}
                      |""".stripMargin.format(version.value, name.value, organization.value, scalaVersion.value))
      Seq(file)
    }.taskValue
  )

  lazy val buildSettings = Seq[Setting[_]](
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.0" cross CrossVersion.full)
  )


  lazy val helpersSettings = Seq[Setting[_]](
    initialCommands := "import me.chuwy.otusbats._"
  )
}
