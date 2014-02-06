package de.sschauss.sle.fsml.generators

import de.sschauss.sle.fsml.Ast.Fsm

object DotGenerator extends Generator {

  val targetLanguage = "dot"

  val languageSuffix = "dot"

  def generateDot(fsm: Fsm) =
    generate("Graph", Map("fsm" -> fsm))

}