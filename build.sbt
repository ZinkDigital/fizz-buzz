

val scala3Version = "3.0.0"
val fs2Version = "3.0.4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "fizz-buzz",
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies += "co.fs2" %% "fs2-core" % fs2Version,
    libraryDependencies += "co.fs2" %% "fs2-io" % fs2Version
  )