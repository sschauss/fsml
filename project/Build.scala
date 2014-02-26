import sbt._
import Keys._

import complete.DefaultParsers._

object GenerateBuild extends Build {

  lazy override val projects = Seq(root)

  lazy val root = Project("root", file(".")) settings (
    commands ++= Seq(generateCommand, testCommand)
    )

  lazy val filename = fileParser(new File("."))
  lazy val languages = oneOf(Seq("all", "java", "dot").map(literal(_)))
  lazy val inputs = tokenDisplay(Space ~> ID, "<input>") *
  lazy val languageCommand = (Space ~> "--language" ~> Space ~> languages)
  lazy val inputCommand = Space ~> "--input" ~> inputs
  lazy val generate = (Space ~> filename) ~ ((languageCommand ?) ~ (inputCommand ?))

  lazy val test = Space ~> oneOf(Seq("parser", "simulator").map(literal(_))) ~ ((Space ~> "--testRuns" ~>  Space ~> (Digit *)) ?)

  def generateCommand = Command("generate")(_ => generate) {
    (state, cli) =>
      val args = cli._1.getAbsolutePath ++ " " ++ (cli._2 match {
        case (Some(language), Some(input)) => language ++ " " ++ input.mkString(" ")
        case (Some(language), None)        => language
        case (None, Some(input))           => "all" ++ " " ++ input.mkString(" ")
        case _                             => "all"
      })
      runWithArgs("run", args, state)
      state
  }

  def testCommand = Command("test")(_ => test) {
    (state, cli) =>
      cli._2 match {
        case Some(r) => {
          val testRuns = r.mkString
          sys.props.+(("-DtestRuns", s"$testRuns"))
        }
        case None =>
      }
      cli._1 match {
        case "parser"    => runWithArgs("test-only", "de.sschauss.sle.fsml.tests.ParserTest", state)
        case "simulator" => runWithArgs("test-only", "de.sschauss.sle.fsml.tests.SimulatorTest", state)
      }
      state
  }

  def runWithArgs(command: String, args: String, state: State) = Command.process(s"$command $args", state)

}