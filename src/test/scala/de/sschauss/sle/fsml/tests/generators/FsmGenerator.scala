package de.sschauss.sle.fsml.tests.generators

import de.sschauss.sle.fsml.Ast._
import org.scalacheck.Gen
import de.sschauss.sle.fsml.Checker

object FsmGenerator {

  def uncheckedFsm = for {
    states <- Gen.listOf(StateGenerator.state(TransitionGenerator.transition))
  } yield Fsm(states)

  def fsm: Gen[Fsm] = for {
    ids <- IdGenerator.ids
    initialId <- Gen.oneOf(ids.toSeq)
    initialState <- StateGenerator.state(true, initialId, TransitionGenerator.transition(ids.toList))
  } yield Fsm(initialState :: ids.-(initialId).map(id => (for {
      state <- StateGenerator.state(false, id, TransitionGenerator.transition(ids.-(id).+(initialId).toList))
    } yield state).sample.get).toList)


  def checkedFsm(exceptions: List[_ >: Exception]) = fsm suchThat {
    fsm => try {
      Checker.check(fsm)
      true
    } catch {
      case e: Exception => !exceptions.contains(e.getClass)
    }
  }

}