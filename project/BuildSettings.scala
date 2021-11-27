import sbt._
import Keys._

object BuildSettings {

  def lightbeamCommonSettings = Def.settings(
    Test / parallelExecution := false,
    // format: off
    Compile / doc / scalacOptions ++= Seq(
      "-groups",
      "-project-version", version.value,
      "-social-links:github::https://github.com/Husenap/lightbeam",
      "-source-links:github://Husenap/lightbeam/main",
      "-siteroot", (ThisBuild / baseDirectory).value.getAbsolutePath()
    )
    // format: on
  )
}
