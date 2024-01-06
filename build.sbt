lazy val scala3Version = "3.3.0"

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "dev.ligature"
ThisBuild / organizationName := "ligature"

val munitVersion        = "1.0.0-M7"
val xodusVersion        = "2.0.1"
val jeromqVersion       = "0.5.3"
val jlineVersion        = "3.23.0"
val scalafxVersion      = "16.0.0-R24"
val jansiVersion        = "2.4.1"
val scalaLoggingVersion = "3.9.4"
val logBackVersion      = "1.2.10"

lazy val gaze = project
  .in(file("gaze"))
  .settings(
    name := "gaze",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % munitVersion % Test
  )
  .disablePlugins(RevolverPlugin)

lazy val wander = project
  .in(file("wander"))
  .settings(
    name := "wander",
    scalaVersion := scala3Version,
    libraryDependencies += "ch.qos.logback" % "logback-classic" % logBackVersion,
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    libraryDependencies += "org.scalameta" %% "munit" % munitVersion % Test,
  )
  .dependsOn(gaze)
  .disablePlugins(RevolverPlugin)

lazy val wanderZeroMQ = project
  .in(file("wander-zeromq"))
  .settings(
    name := "wander-zeromq",
    scalaVersion := scala3Version,
    libraryDependencies += "ch.qos.logback" % "logback-classic" % logBackVersion,
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    libraryDependencies += "org.zeromq" % "jeromq" % jeromqVersion,
    libraryDependencies += "org.scalameta" %% "munit" % munitVersion % Test,
    fork := true,
  )
  .dependsOn(wander)

disablePlugins(RevolverPlugin)

addCommandAlias("cd", "project")
