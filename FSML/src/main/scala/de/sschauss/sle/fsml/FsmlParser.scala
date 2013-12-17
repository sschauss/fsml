package de.sschauss.sle.fsml

import org.kiama.util.PositionedParserUtilities
import de.sschauss.sle.fsml.FsmlAst._
import scala.language.postfixOps

object FsmlParser extends PositionedParserUtilities {

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