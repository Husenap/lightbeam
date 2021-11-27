ThisBuild / organization := "Husenap"
ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "3.1.0"

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val lightbeamCore = lightbeamProject("lightbeam-core")
  .settings(Dependencies.lightbeamCore)

lazy val example = lightbeamProject("lightbeam-example")
  .settings(
    Compile / run / fork := true
  )
  .dependsOn(lightbeamCore)

def lightbeamProject(name: String): Project =
  Project(id = name, base = file(name))
    .settings(BuildSettings.lightbeamCommonSettings: _*)

lazy val lightbeam = (project in file("."))
  .settings(BuildSettings.lightbeamCommonSettings: _*)
  .aggregate(lightbeamCore)
