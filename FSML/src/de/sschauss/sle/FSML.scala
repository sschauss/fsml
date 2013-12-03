package de.sschauss.sle

import scala.io.Source
import scala.util.parsing.combinator.JavaTokenParsers

case class Fsm(val states: List[State])

case class State(val initial: Boolean, val id: String, val transitions: List[Transition])

case class Transition(val input: String, val action: Option[String], val id: Option[String])

object FsmlParser extends JavaTokenParsers {

  def fsm: Parser[Fsm] = rep(state) ^^ {
    case states => Fsm(states)
  }

  def state: Parser[State] = initial ~ "state" ~ id ~ "{" ~ rep(transition) ~ "}" ^^ {
    case initial ~ _ ~ id ~ _ ~ transitions ~ _ => State(initial, id, transitions)
  }

  def initial: Parser[Boolean] = "initial" ^^ { _ => true } | "" ^^ { _ => false }

  def transition: Parser[Transition] = input ~ opt("/" ~ action) ~ opt("->" ~ id) ~ ";" ^^ {
    case input ~ None ~ None ~ _ => Transition(input, None, None)
    case input ~ Some(_ ~ action) ~ None ~ _ => Transition(input, Some(action), Some(input))
    case input ~ None ~ Some(_ ~ id) ~ _ => Transition(input, None, Some(id))
    case input ~ Some(_ ~ action) ~ Some(_ ~ id) ~ _ => Transition(input, Some(action), Some(id))
  }

  def id: Parser[String] = "[A-Za-z0-9]*".r

  def input: Parser[String] = "[A-Za-z0-9]*".r

  def action: Parser[String] = "[A-Za-z0-9]*".r

}

object FsmChecker {

  def okFsm(fsm: Fsm): Boolean =
    fsmSingleInitial(fsm)

  def fsmSingleInitial(fsm: Fsm): Boolean =
    fsm.states.filter(state => state.initial).length == 1

}

object Runner {

  def main(args: Array[String]) {
    val fsmlParser = FsmlParser
    val input = Source.fromFile(args(0)).mkString
    val output = fsmlParser.parseAll(fsmlParser.fsm, input)
  }

}