package de.sschauss.sle

import org.fusesource.scalate.TemplateEngine
import scala.reflect.io.Path
import de.sschauss.sle.fsml.FsmlAst._
import de.sschauss.sle.fsml.FsmlAst.Fsm

package object generators {

  sealed trait Generator {

    val templateEngine = new TemplateEngine

    val templateBaseDir = "src/main/resources/templates"

    val generateBaseDir = "src-gen/main"

    val targetLanguage: String

    val languageSuffix: String

    def generate(filename: String, values: Map[String, AnyRef]) =
      Path(s"$generateBaseDir/$targetLanguage/$filename.$languageSuffix").toFile.writeAll(templateEngine.layout(s"$templateBaseDir/$targetLanguage/$filename.ssp", values))

  }

  object FsmJavaGenerator extends Generator {

    val targetLanguage = "java"

    val languageSuffix = "java"

    def generateJava(fsm: Fsm, sampleInput: List[Name]) = {
      generate("Action", Map("fsm" -> fsm))
      generate("Input", Map("fsm" -> fsm))
      generate("State", Map("fsm" -> fsm))
      generate("Stepper", Map("fsm" -> fsm, "sampleInput" -> sampleInput))
    }

  }


  object FsmDotGenerator extends Generator {

    val targetLanguage = "dot"

    val languageSuffix = "dot"

    def generateDot(fsm: Fsm) =
      generate("Graph", Map("fsm" -> fsm))

  }

}
