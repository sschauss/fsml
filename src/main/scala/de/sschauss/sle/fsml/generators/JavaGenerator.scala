package de.sschauss.sle.fsml.generators

import de.sschauss.sle.fsml.Ast._

object JavaGenerator extends Generator {

  val targetLanguage = "java"

  val languageSuffix = "java"

  def generateJava(fsm: Fsm, sampleInput: List[Name]) = {
    generate("Action", Map("fsm" -> fsm))
    generate("Input", Map("fsm" -> fsm))
    generate("State", Map("fsm" -> fsm))
    generate("Stepper", Map("fsm" -> fsm, "sampleInput" -> sampleInput))
  }

}
