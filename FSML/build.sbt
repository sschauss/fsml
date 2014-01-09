import sbt._
import Keys._

name := "Fsml"

version := "1.0"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-feature")

libraryDependencies ++= Seq(
  "com.googlecode.kiama" % "kiama_2.10" % "1.5.1",
  "org.fusesource.scalate" % "scalate-core_2.10" % "1.6.1",
  "org.slf4j" % "slf4j-log4j12" % "1.7.5",
  "org.specs2" % "specs2_2.10" % "2.3.4"
)