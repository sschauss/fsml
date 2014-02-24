package de.sschauss.sle.fsml.tests

import org.scalatest.FreeSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import de.sschauss.sle.fsml.Ast._
import de.sschauss.sle.fsml.prettyprinters.ConcreteSyntaxPrettyPrinter
import de.sschauss.sle.fsml.{Simulator, Parser}
import de.sschauss.sle.fsml.exceptions._
import de.sschauss.sle.fsml.tests.generators.{InputGenerator, FsmGenerator}

class GeneratedTest extends FreeSpec with GeneratorDrivenPropertyChecks {

  implicit override val generatorDrivenConfig = PropertyCheckConfig(minSuccessful = 24)

  "FsmlParser should" - {
    "parse successful all generated test data" in {
      forAll(
        FsmGenerator.fsm(
          Seq(
            classOf[DeterministicException], //TODO test without this exception, maybe simulator still works
            classOf[DistinctIdException],
            classOf[InitialStateException],
            classOf[ReachableException],
            classOf[ResolvableException])
        )
      ) {
        (fsm: Fsm) =>
          val cs = ConcreteSyntaxPrettyPrinter.pretty(fsm)
          val parsedFsm = Parser.parse(cs)
          Simulator.simulate(fsm, InputGenerator.input(fsm).sample.get)
          assert(fsm == parsedFsm)
      }
    }
  }

}