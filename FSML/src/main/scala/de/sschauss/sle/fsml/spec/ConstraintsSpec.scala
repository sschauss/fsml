package de.sschauss.sle.fsml.spec

import org.specs2.mutable.Specification
import de.sschauss.sle.fsml.FsmlAst._
import de.sschauss.sle.fsml.FsmlAst

class ConstraintsSpec(val fsm: Fsm) extends Specification {

  "The FSM" should {
    "has one and only one initial state." in {
      fsm.states.filter(state => state.initial) must have size (1)
    }
    "has only distinct state ids." in {
      val ids = for (state <- fsm.states) yield state.id
      ids.toSet must have size (fsm.states.size)
    }
    "handles input deterministic." in {
      val uniqueInputList = for (state <- fsm.states) yield {
        val inputs = for (transition <- state.transitions) yield transition.input
        inputs.toSet.size == inputs.size
      }
      uniqueInputList.forall(x => x)
    }
    "has only defined referenced target states" in {
      val targetStateIds = for(state <- fsm.states; transition <- state.transitions; if(transition.id.isDefined)) yield transition.id.get
      val definedStateIds = for(state <- fsm.states) yield state.id
      targetStateIds.toSet -- definedStateIds.toSet must have size(0)
    }
    "has only reacheable states." in {
      val initial = fsm.states.filter(state => state.initial).head
      fsm.states.collect({case State(_, id, _) => id}).toSet -- reachable(initial) must have size(0)
    }
  }

  def reachable(state: State): Set[Name] = reachable(Set(state.id), Set())

  def reachable(statesToVisit: Set[Name], reachableStates: Set[Name]): Set[Name] = statesToVisit.toSeq match {
    case Seq() => reachableStates
    case Seq(x, xs@_*) => {
      val states = for(transition <- fsm.states.filter(state => state.id == x).head.transitions; if(transition.id.isDefined)) yield transition.id.get
      reachable((states.toSet ++ statesToVisit) -- (reachableStates + x), reachableStates + x)
    }
  }

}

