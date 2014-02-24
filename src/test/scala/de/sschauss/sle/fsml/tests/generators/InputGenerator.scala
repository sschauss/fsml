package de.sschauss.sle.fsml.tests.generators

import de.sschauss.sle.fsml.Ast._

import org.scalacheck.Gen


object InputGenerator {

  def input(fsm: Fsm): Gen[Seq[Name]] = for {
    size <- Gen.chooseNum(0, 100)
  } yield input(Seq(), size, fsm.states.find(_.initial).get, fsm)

  def input(is: Seq[Name], size: Int, state: State, fsm: Fsm): Seq[Name] = size match {
    case 0 => is
    case _ => state.transitions match {
      case List() => is
      case _      => Gen.oneOf(state.transitions).sample.get match {
        case Transition(i, _, None)     => input(is :+ i, size - 1, state, fsm)
        case Transition(i, _, Some(id)) => input(is :+ i, size - 1, fsm.states.find(_.id == id).get, fsm)
      }
    }
  }

}
