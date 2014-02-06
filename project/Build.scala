import sbt._
import Keys._

import complete.DefaultParsers._
import sbt.complete.Parser

object GenerateBuild extends Build {

  lazy override val projects = Seq(root)
  
  lazy val root = Project("root", file(".")) settings (
    commands ++= Seq(generateCommand)
    )

  lazy val filename = fileParser(new File("."))
  lazy val languages = oneOf(for (language <- Seq("all", "java", "dot")) yield literal(language))
  lazy val inputs = tokenDisplay(Space ~> ID, "<input>") *
  lazy val languageCommand = (Space ~> "--language" ~> Space ~> languages)
  lazy val inputCommand = Space ~> "--input" ~> inputs
  lazy val generate = (Space ~> filename) ~ ((languageCommand ?) ~ (inputCommand ?))

  def generateCommand = Command("generate")(_ => generate) {
    (state, cli) =>
      val args = cli._1.getAbsolutePath ++ " " ++ (cli._2 match {
        case (Some(language), Some(input)) => language ++ " " ++ input.mkString(" ")
        case (Some(language), None) => language
        case (None, Some(input)) => "all" ++ " " ++ input.mkString(" ")
        case _ => "all"
      })
      runWithArgs(args, state)
      state
  }

  def runWithArgs(args: String, state: State) = Command.process(s"run $args", state)

}