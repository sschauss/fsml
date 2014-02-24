package de.sschauss.sle.fsml

import de.sschauss.sle.fsml.Ast._
import de.sschauss.sle.fsml.exceptions._


object Simulator {

  def simulate(fsm: Fsm, input: Seq[Name]): Seq[(Option[Name], Name)] = {
    fsm.states.find(_.initial) match {
      case Some(state) => simulate(fsm, state, input, Seq())
      case _           => throw new InitialStateException
    }
  }

  def simulate(fsm: Fsm, currentState: State, input: Seq[Name], output: Seq[(Option[Name], Name)]): Seq[(Option[Name], Name)] = {
    val from = currentState.id
    input match {
      case List()  => output
      case i :: is => currentState.transitions.find(_.input == i) match {
        case Some(transition) => transition.id match {
          case Some(id) => fsm.states.find(_.id == id) match {
            case Some(state) => {
              println(s"    from: $from, input: $i, to: $id")
              transition.action match {
                case Some(action) => println(s"    action")
                case _            => Unit
              }
              simulate(fsm, state, is, output :+(transition.action, id))
            }
            case _           => throw new ResolvableException(s"unresolvable state id $id")
          }
          case None     => {
            println(s"    from: $from, input: $i, to: $from")
            simulate(fsm, currentState, is, output :+(transition.action, currentState.id))
          }
        }
        case None             => throw new IllegalInputException(s"$i, possible alternatives: ${currentState.transitions.map(_.input).mkString(", ")}")
      }
    }
  }

}