package de.sschauss.sle.fsml.tests

import org.scalatest.FreeSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import de.sschauss.sle.fsml.Ast._
import de.sschauss.sle.fsml.prettyprinters.ConcreteSyntaxPrettyPrinter
import de.sschauss.sle.fsml.Parser
import de.sschauss.sle.fsml.tests.generators.FsmGenerator

class ParserTest extends FreeSpec with GeneratorDrivenPropertyChecks {

  implicit override val generatorDrivenConfig = PropertyCheckConfig(minSuccessful = 100)

  "FsmlParser should" - {
    "parse successful all generated test data" in {
      forAll(
        FsmGenerator.uncheckedFsm
      ) {
        (fsm: Fsm) =>
          val cs = ConcreteSyntaxPrettyPrinter.pretty(fsm)
          val parsedFsm = Parser.parse(cs)
          assert(fsm == parsedFsm)

      }
    }
  }

}