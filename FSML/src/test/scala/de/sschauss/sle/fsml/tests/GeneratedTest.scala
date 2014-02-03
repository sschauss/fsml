package de.sschauss.sle.fsml.tests

import org.scalatest.{Matchers, FreeSpec}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import de.sschauss.sle.fsml.Ast._
import de.sschauss.sle.fsml.prettyprinters.ConcreteSyntaxPrettyPrinter
import de.sschauss.sle.fsml.{Checker, Parser}

class GeneratedTest extends FreeSpec with Matchers with GeneratorDrivenPropertyChecks {

  implicit override val generatorDrivenConfig = PropertyCheckConfig(minSuccessful = 10)

  var i = 0

  "FsmlParser should" - {
    "parse successful all generated test data" in {
      println("#", "states", "transitions")
      forAll(ValidAstGenerator.fsm) {
        (fsm: Fsm) =>
          val cs = ConcreteSyntaxPrettyPrinter.pretty(fsm)
          val parsedFsm = Parser.parse(cs)
          Checker.check(fsm)
          i += 1
          println(i, fsm.states.size, fsm.states.map(_.transitions.size).foldLeft(0)(_ + _))
          assert(fsm == parsedFsm)
      }
    }
  }

}