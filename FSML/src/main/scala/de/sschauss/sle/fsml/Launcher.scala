package de.sschauss.sle.fsml

import scala.io.Source

object Launcher extends App {

  val parser = FsmlParser
  val prettyPrinter = FsmlPrettyPrinter

  val input = Source.fromFile("src/main/resources/sample.fsml").mkString
  val output = parser.parseAll(parser.parser, input)
  val pretty = prettyPrinter.pretty(output.get)


  println(output.get)
  println(pretty)

  val prettyStates = prettyPrinter.prettyStates(output.get)
  println(prettyStates)

  val sampleInput = List("ticket", "pass", "ticket", "pass", "ticket", "ticket", "pass", "pass", "ticket", "pass", "mute", "release", "ticket", "pass")

  val simulator = Simulator
  println(simulator.simulate(output.get, sampleInput))

  //val generator = FsmlGenerator
  //generator.generateJava(output.get, sampleInput)

}