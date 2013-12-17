package de.sschauss.sle.fsml

import de.sschauss.sle.fsml.FsmlAst._
import org.fusesource.scalate.{RenderContext, Binding, TemplateEngine}
import java.io.PrintWriter
import scala.io.Source
import scala.reflect.io.Path

object FsmlGenerator {

  val templateEngine = new TemplateEngine

  val templateBaseDir = "src/main/resources/templates"

  val generateBaseDir = "src-gen/main"


  def generateJava(fsm: Fsm, sampleInput: List[Name]) = {
    Path(s"$generateBaseDir/java/Action.java").toFile.writeAll(templateEngine.layout(s"$templateBaseDir/java/Action.ssp", Map("fsm" -> fsm)))
    Path(s"$generateBaseDir/java/Input.java").toFile.writeAll(templateEngine.layout(s"$templateBaseDir/java/Input.ssp", Map("fsm" -> fsm)))
    Path(s"$generateBaseDir/java/State.java").toFile.writeAll(templateEngine.layout(s"$templateBaseDir/java/State.ssp", Map("fsm" -> fsm)))
    Path(s"$generateBaseDir/java/Stepper.java").toFile.writeAll(templateEngine.layout(s"$templateBaseDir/java/Stepper.ssp", Map("fsm" -> fsm, "sampleInput" -> sampleInput)))

  }
}
