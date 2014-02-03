package de.sschauss.sle.fsml

import scala.io.Source
import de.sschauss.sle.fsml.generators.{DotGenerator, JavaGenerator}

object Main extends App {
  val filename = args(0)
  val language = args(1)
  val input = args.slice(2, args.length).toList

  print(s"- loading $filename")
  val cs = Source.fromFile(filename).mkString
  ok

  print("- parsing")
  val fsm = Parser.parse(cs)
  ok

  print("- checking")
  Checker.check(fsm)
  ok

  println("- simulating")
  Simulator.simulate(fsm, input)
  print("-")
  ok

  print(s"- generate $language source code")
  language match {
    case "all"  => {
      JavaGenerator.generateJava(fsm, input)
      DotGenerator.generateDot(fsm)
    }
    case "java" => JavaGenerator.generateJava(fsm, input)
    case "dot"  => DotGenerator.generateDot(fsm)
  }
  ok

  def ok = println( s"""\033[32m OK\033[0m""")

}