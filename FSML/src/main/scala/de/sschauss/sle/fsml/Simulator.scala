package de.sschauss.sle.fsml

import de.sschauss.sle.fsml.FsmlAst._


object Simulator {

  def simulate(fsm: Fsm, input: List[Name]): List[(Option[Name], Name)] = {
    simulate(fsm, fsm.states.filter(state => state.initial).head, input, List())
  }

  def simulate(fsm: Fsm, currentState: State, input: List[Name], output: List[(Option[Name], Name)]): List[(Option[Name], Name)] = (currentState, input) match {
    case (_, List()) => output
    case (state, i :: is) =>
      state.transitions.filter(transition => transition.input.equals(i)).head match {
        case Transition(_, action, Some(id)) => simulate(fsm, fsm.states.filter(state => state.id.equals(id)).head, is, output ::: List((action, id)))
        case Transition(_, action, None) => simulate(fsm, currentState, is, output ::: List((action, currentState.id)))
      }
  }

}