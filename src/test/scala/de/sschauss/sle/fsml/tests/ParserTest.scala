package de.sschauss.sle.fsml.tests

import de.sschauss.sle.fsml.Ast._
import de.sschauss.sle.fsml.prettyprinters.ConcreteSyntaxPrettyPrinter
import de.sschauss.sle.fsml.Parser
import de.sschauss.sle.fsml.tests.generators.FsmGenerator

class ParserTest extends GeneratedTest {

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