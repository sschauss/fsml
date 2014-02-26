package de.sschauss.sle.fsml.tests

import de.sschauss.sle.fsml.tests.generators.{InputGenerator, FsmGenerator}
import de.sschauss.sle.fsml.exceptions._
import de.sschauss.sle.fsml.Ast.Fsm
import de.sschauss.sle.fsml.Simulator
import org.scalatest.FreeSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import de.sschauss.sle.fsml.generators.DotGenerator


class SimulatorTest extends GeneratedTest {

  "Simulator should" - {
    "process successful process input for valid fsms" in {
      forAll(
        FsmGenerator.checkedFsm(
          List(
            classOf[DeterministicException],
            classOf[DistinctIdException],
            classOf[InitialStateException],
            classOf[ReachableException],
            classOf[ResolvableException])
        )
      ) {
        (fsm: Fsm) =>
          DotGenerator.generateDot(fsm)
          Simulator.simulate(fsm, InputGenerator.input(fsm, 100).sample.get)
      }
    }
  }
}
