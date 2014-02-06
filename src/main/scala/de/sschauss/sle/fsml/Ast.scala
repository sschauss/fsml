package de.sschauss.sle.fsml

import org.kiama.attribution.Attributable
import org.kiama.util.Positioned

object Ast {

  type Name = String

  trait FsmlNode extends Attributable with Positioned

  case class Fsm(val states: List[State]) extends FsmlNode

  case class State(val initial: Boolean, val id: Name, val transitions: List[Transition]) extends FsmlNode

  case class Transition(val input: Name, val action: Option[Name], val id: Option[Name]) extends FsmlNode

}