name := """play-reveal"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "angularjs" % "1.2.23",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.scala-lang" % "scala-compiler" % "2.11.2"
)
