package de.sschauss.sle.fsml

import de.sschauss.sle.fsml.Ast._
import de.sschauss.sle.fsml.exceptions._


object Checker {

  def check(fsm: Fsm) = {
    fsmSingleInitial(fsm)
    fsmDistinctIds(fsm)
    fsmResolvable(fsm)
    fsmDeterministic(fsm)
    fsmReachable(fsm)
  }

  def fsmSingleInitial(fsm: Fsm) = fsm.states.filter(_.initial).size match {
    case 1 => true
    case _ => throw new InitialStateException
  }

  def fsmDistinctIds(fsm: Fsm) = fsm.states.map(_.id).toSet.size - fsm.states.size match {
    case 0 => true
    case _ => throw new DistinctIdException
  }

  def fsmResolvable(fsm: Fsm) = (fsm.states.map(_.transitions).flatten.map(_.id).flatten.toSet -- fsm.states.map(_.id).toSet).size match {
    case 0 => true
    case _ => throw new ResolvableException
  }

  def fsmDeterministic(fsm: Fsm) = fsm.states.map(t => {
    t.transitions.map(_.input).toSet.size == t.transitions.size
  }).reduce(_ && _) match {
    case true => true
    case _    => throw new DeterministicException
  }

  def fsmReachable(fsm: Fsm) = (fsm.states.map(_.id).toSet -- reachable(fsm, fsm.states.filter(_.initial).head)).size match {
    case 0 => true
    case _ => throw new ReachableException
  }

  def reachable(fsm: Fsm, state: State): Set[Name] = reachable(fsm, Set(state.id), Set())

  def reachable(fsm: Fsm, statesToVisit: Set[Name], reachableStates: Set[Name]): Set[Name] = statesToVisit.toSeq match {
    case Seq()         => reachableStates
    case Seq(x, xs@_*) => {
      val transitions = fsm.states.find(state => state.id == x) match {
        case Some(state) => state.transitions
        case _           => throw new ResolvableException
      }
      val states = for (transition <- transitions; if transition.id.isDefined) yield transition.id.get
      reachable(fsm, (states.toSet ++ statesToVisit) -- (reachableStates + x), reachableStates + x)
    }
  }

}

