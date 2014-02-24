package de.sschauss.sle.fsml.tests.generators

import org.scalacheck.Gen
import de.sschauss.sle.fsml.Ast._

object StateGenerator {

  def state(transition: Gen[Transition]) = for {
    initial <- Gen.oneOf(true, false)
    id <- Gen.identifier
    transitions <- Gen.listOf(transition)
  } yield State(initial, id, transitions)

  def state(initial: Boolean, transition: Gen[Transition]) = for {
    id <- Gen.identifier
    transitions <- Gen.listOf(transition)
  } yield State(initial, id, transitions)

  def state(id: Name, transition: Gen[Transition]) = for {
    initial <- Gen.oneOf(true, false)
    transitions <- Gen.listOf(transition)
  } yield State(initial, id, transitions)

  def state(initial: Boolean, id: Name, transition: Gen[Transition]) = for {
    transitions <- Gen.listOf(transition)
  } yield State(initial, id, transitions)

}
