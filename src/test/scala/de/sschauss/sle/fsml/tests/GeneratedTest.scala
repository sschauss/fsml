package de.sschauss.sle.fsml.tests

import org.scalatest.FreeSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks

trait GeneratedTest extends FreeSpec with GeneratorDrivenPropertyChecks {

  val testRuns:Int  = sys.props.get("-DtestRuns").getOrElse("100").toInt

  implicit override val generatorDrivenConfig = PropertyCheckConfig(minSuccessful = testRuns)

}
