package de.sschauss.sle.fsml

import scala.io.Source
import de.sschauss.sle.generators.{FsmDotGenerator, FsmJavaGenerator}

object Main extends App {
  val filename = args(0)
  val language = args(1)
  val input = args.slice(2, args.length).toList

  print(s"- loading $filename")
  val cs = Source.fromFile(filename).mkString
  ok

  print("- parsing")
  val fsm = FsmlParser.parse(cs)
  ok

  print("- checking")
  FsmChecker.check(fsm)
  ok

  println("- simulating")
  FsmlSimulator.simulate(fsm, input)
  print("-")
  ok

  print(s"- generate $language source code")
  language match {
    case "all"  => {
      FsmJavaGenerator.generateJava(fsm, input)
      FsmDotGenerator.generateDot(fsm)
    }
    case "java" => FsmJavaGenerator.generateJava(fsm, input)
    case "dot"  => FsmDotGenerator.generateDot(fsm)
  }
  ok

  def ok = println( s"""\033[32m OK\033[0m""")

}