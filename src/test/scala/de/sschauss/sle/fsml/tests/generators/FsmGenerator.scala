package de.sschauss.sle.fsml.tests.generators

import de.sschauss.sle.fsml.Ast._
import org.scalacheck.Gen
import de.sschauss.sle.fsml.Checker

object FsmGenerator {

  def fsm: Gen[Fsm] = for {
    ids <- Gen.containerOf[Set, Name](Gen.identifier)
    initialId <- Gen.oneOf(ids.toSeq)
    initialState <- StateGenerator.state(true, initialId, TransitionGenerator.transition(ids.toSeq))
  } yield Fsm(initialState :: ids.-(initialId).map(id => (for {
      state <- StateGenerator.state(false, id, TransitionGenerator.transition(ids.-(id).+(initialId).toSeq))
    } yield state).sample.get).toList)

  def fsm(exceptions: Seq[_ >: Exception]): Gen[Fsm] = for {
    fsm <- fsm suchThat {
      fsm => try {
        Checker.check(fsm)
        true
      } catch {
        case e: Exception => {
          println(e.getClass, !exceptions.contains(e.getClass))
          !exceptions.contains(e.getClass)
        }
      }
    }
  } yield fsm


}