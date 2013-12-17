package de.sschauss.sle.fsml.spec

import org.specs2.mutable.Specification
import de.sschauss.sle.fsml._
import scala.io.Source

class ConstraintsSpec(val cs: String) extends Specification {

  val input = Source.fromFile(cs).mkString
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

