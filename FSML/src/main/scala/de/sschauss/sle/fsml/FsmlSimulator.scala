package de.sschauss.sle.fsml

import de.sschauss.sle.fsml.FsmlAst._
import de.sschauss.sle.fsml.exceptions._


object FsmlSimulator {

  def simulate(fsm: Fsm, input: List[Name]): List[(Option[Name], Name)] = {
    fsm.states.find(_.initial) match {
      case Some(state) => simulate(fsm, state, input, List())
      case _ => throw new InitialStateException
    }
  }

  def simulate(fsm: Fsm, currentState: State, input: List[Name], output: List[(Option[Name], Name)]): List[(Option[Name], Name)] = {
    val from = currentState.id
    input match {
      case List() => output
      case i :: is => currentState.transitions.find(_.input == i) match {
        case Some(transition) => transition.id match {
          case Some(id) => fsm.states.find(_.id == id) match {
            case Some(state) => {
              println(s"from: $from, input: $i, to: $id")
              transition.action match {
                case Some(action) => println(action)
                case _ =>
              }
              simulate(fsm, state, is, output.::((transition.action, id)))
            }
            case _ => throw new ResolvableException
          }
          case None => {
            println(s"from: $from, input: $i, to: $from")
            simulate(fsm, currentState, is, output.::((transition.action, currentState.id)))
          }
        }
        case None => throw new IllegalInputException(i)
      }
    }
  }

}