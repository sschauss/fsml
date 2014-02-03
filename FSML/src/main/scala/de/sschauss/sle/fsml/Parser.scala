package de.sschauss.sle.fsml

import org.kiama.util.PositionedParserUtilities
import de.sschauss.sle.fsml.Ast._
import scala.language.postfixOps
import de.sschauss.sle.fsml.exceptions.ParseException
import scala.io.BufferedSource

object Parser extends PositionedParserUtilities {

  def parse(cs: BufferedSource): Fsm = parse(cs.mkString)

  def parse(cs: String): Fsm = parseAll(parser, cs) match {
    case Success(fsm, _)     => fsm
    case Failure(message, _) => throw new ParseException(message)
    case Error(message, _)   => throw new ParseException(message)
  }

  lazy val parser =
    phrase(fsm)

  lazy val fsm: PackratParser[Fsm] =
    rep(state) ^^ Fsm

  lazy val state: PackratParser[State] =
    (initial <~ "state") ~ id ~ ("{" ~> (transition *) <~ "}") ^^ State

  lazy val transition: PackratParser[Transition] =
    input ~ (("/" ~> action) ?) ~ (("->" ~> id) ?) <~ ";" ^^ Transition

  lazy val initial: PackratParser[Boolean] =
    "initial" ^^^ true |
      "" ^^^ false

  lazy val id =
    name

  lazy val input =
    name

  lazy val action =
    name

  lazy val name =
    "[a-zA-Z0-9]+".r

}