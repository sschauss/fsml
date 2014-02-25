package de.sschauss.sle.fsml.tests.generators

import de.sschauss.sle.fsml.Ast._

import org.scalacheck.Gen


object InputGenerator {

  def input(fsm: Fsm, maxSize: Int) = for {
    size <- Gen.chooseNum(0, maxSize)
  } yield inputs(size, fsm.states.find(_.initial).get, fsm)

  private def inputs(size: Int, state: State, fsm: Fsm, is: List[Name] = List()): List[Name] = size match {
    case 0 => is
    case _ => state.transitions match {
      case List() => is
      case _      => Gen.oneOf(state.transitions).sample.get match {
        case Transition(i, _, None)     => inputs(size - 1, state, fsm, is :+ i)
        case Transition(i, _, Some(id)) => inputs(size - 1, fsm.states.find(_.id == id).get, fsm, is :+ i)
      }
    }
  }

}
