package de.sschauss.sle.fsml.tests

import org.scalatest.FreeSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import de.sschauss.sle.fsml.Ast._
import de.sschauss.sle.fsml.prettyprinters.ConcreteSyntaxPrettyPrinter
import de.sschauss.sle.fsml.Parser

class GeneratedTest extends FreeSpec with GeneratorDrivenPropertyChecks {

  implicit override val generatorDrivenConfig = PropertyCheckConfig(minSuccessful = 10)

  "FsmlParser should" - {
    "parse successful all generated test data" in {
      forAll(ValidAstGenerator.fsm) {
        (fsm: Fsm) =>
          val cs = ConcreteSyntaxPrettyPrinter.pretty(fsm)
          val parsedFsm = Parser.parse(cs)
          assert(fsm == parsedFsm)
      }
    }
  }

}