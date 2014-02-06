package de.sschauss.sle.fsml.tests

import de.sschauss.sle.fsml.Ast._
import org.scalacheck.Gen
import de.sschauss.sle.fsml.{Ast, Checker}

object ValidAstGenerator {

  def fsm = (for {
    ids <- ids
    states <- states(ids)
  } yield Fsm(states)) suchThat (fsm => try {
    Checker.check(fsm)
    true
  } catch {
    case e: Exception => false
  })

  def states(ids: Set[Name]): Gen[List[State]] = for {
    initialState <- state(true, ids)
  } yield ids.map(id =>
      (for {
        transitions <- Gen.listOf(transition(ids.-(id).+(initialState.id)))
      } yield State(false, id, transitions)).sample.get
    ).+(initialState).toList


  def state(initial: Boolean, ids: Set[Name]) = for {
    id <- id
    transitions <- transitions(ids)
  } yield State(initial, id, transitions)

  def transitions(ids: Set[Name]) = Gen.listOf(transition(ids))

  def transition(ids: Set[Name]) = for {
    input <- Gen.identifier
    action <- Gen.option(Gen.identifier)
    id <- Gen.option(Gen.oneOf(ids.toList))
  } yield Transition(input, action, id)


  def id = Gen.identifier

  def ids: Gen[Set[Ast.Name]] = Gen.containerOf[Set, Name](Gen.identifier)

}