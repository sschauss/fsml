name := "Fsml"

version := "1.0"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-feature")

libraryDependencies ++= Seq(
  "com.googlecode.kiama" % "kiama_2.10" % "1.5.1",
  "org.fusesource.scalate" % "scalate-core_2.10" % "1.6.1",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "org.specs2" % "specs2_2.10" % "2.3.4"
)
