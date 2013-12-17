package de.sschauss.sle.fsml

import scala.io.Source

import org.specs2.mutable.Specification

class ConstraintsSpec extends Specification {

  val input = Source.fromFile("src/test/resources/sample.fsml").mkString
  val fsm = FsmlParser.parseAll(FsmlParser.parser, input).get

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
  }

}