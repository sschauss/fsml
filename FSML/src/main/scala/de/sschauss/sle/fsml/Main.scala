package de.sschauss.sle.fsml

import scala.io.Source
import de.sschauss.sle.fsml.spec.ConstraintsSpec

object Main extends App {
  val parser = FsmlParser
  val generator = FsmlGenerator
  val simulator = FsmlSimulator
  val input = Source.fromFile(args(0)).mkString
  val output = parser.parseAll(parser.parser, input)
  val sampleInput = args.slice(1, args.length).toList

  specs2.run(new ConstraintsSpec(output.get))
  val simOutput = simulator.simulate(output.get, sampleInput)
  println(simOutput)
  generator.generateJava(output.get, sampleInput)

}