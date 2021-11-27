import sbt._
import Keys._

object Dependencies {
  object Test {
    val scalatest = "org.scalatest" %% "scalatest" % "3.2.10"
  }

  val l             = libraryDependencies
  val lightbeamCore = l ++= Seq(Test.scalatest)
}
