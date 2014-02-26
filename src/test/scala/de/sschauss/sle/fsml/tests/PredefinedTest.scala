package de.sschauss.sle.fsml.tests

import de.sschauss.sle.fsml.exceptions._
import de.sschauss.sle.fsml.{Checker, Parser}
import scala.io.Source
import org.scalatest.FreeSpec

class PredefinedTest extends FreeSpec {

  val testResourceBaseDir = "src/test/resources"

  val parserError = s"$testResourceBaseDir/parserError.fsml"
  val initialNotOk = s"$testResourceBaseDir/initialNotOk.fsml"
  val idsNotOk = s"$testResourceBaseDir/idsNotOk.fsml"
  val resolutionNotOk = s"$testResourceBaseDir/resolutionNotOk.fsml"
  val determinismNotOk = s"$testResourceBaseDir/determinismNotOk.fsml"
  val reachabilityNotOk = s"$testResourceBaseDir/reachabilityNotOk.fsml"

  "The Fsm" - {
    "throw a ParseException for parserError.fsml" in {
      intercept[ParseException] {
        Parser.parse(Source.fromFile(parserError))
      }
    }
    "throw a InitialStateException for initialNotOk.fsml" in {
      intercept[InitialStateException] {
        Checker.fsmSingleInitial(Parser.parse(Source.fromFile(initialNotOk)))
      }
    }
    "throw a DistinctIdException for idsNotOk.fsml" in {
      intercept[DistinctIdException] {
        Checker.fsmDistinctIds(Parser.parse(Source.fromFile(idsNotOk)))
      }
    }
    "throw a ResolvableException for resolutionNotOk.fsml" in {
      intercept[ResolvableException] {
        Checker.fsmResolvable(Parser.parse(Source.fromFile(resolutionNotOk)))
      }
    }
    "throw a DeterministicException for deterministicNotOk.fsml" in {
      intercept[DeterministicException] {
        Checker.fsmDeterministic(Parser.parse(Source.fromFile(determinismNotOk)))
      }
    }
    "throw a ReachableException for reachabilityNotOk.fsml" in {
      intercept[ReachableException] {
        Checker.fsmReachable(Parser.parse(Source.fromFile(reachabilityNotOk)))
      }
    }
  }

}
