package de.sschauss.sle.fsml

import scala.io.Source
import de.sschauss.sle.generators.{FsmDotGenerator, FsmJavaGenerator}

object Main extends App {
  val filename = args(0)
  val language = args(1)
  val input = args.slice(2, args.length).toList

  val cs = Source.fromFile(filename).mkString

  println(
    s"""
      |parse concrete syntax
    """.stripMargin
  )
  val fsm = FsmlParser.parse(cs)
  println(fsm)

  FsmChecker.check(fsm)


  println(
    s"""
      |simulate FSM with given input
    """.stripMargin
  )
  FsmlSimulator.simulate(fsm, input)

  println(
    s"""
      |generate source code
    """.stripMargin
  )
  language match {
    case "all" => {
      FsmJavaGenerator.generateJava(fsm, input)
      FsmDotGenerator.generateDot(fsm)
    }
    case "java" => FsmJavaGenerator.generateJava(fsm, input)
    case "dot" => FsmDotGenerator.generateDot(fsm)
  }

}