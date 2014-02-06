package de.sschauss.sle.fsml.generators

import java.io.File
import org.fusesource.scalate.TemplateEngine
import scala.reflect.io.Path

trait Generator {

  val templateEngine = new TemplateEngine

  val templateBaseDir = "src/main/resources/templates"

  val generateBaseDir = "src-gen/main"

  val targetLanguage: String

  val languageSuffix: String

  def generate(filename: String, values: Map[String, AnyRef]) = {
    new File(s"$generateBaseDir/$targetLanguage/${filename.split("/").take(filename.split("/").length - 1).mkString("/")}").mkdirs()
    Path(s"$generateBaseDir/$targetLanguage/$filename.$languageSuffix").toFile.writeAll(templateEngine.layout(s"$templateBaseDir/$targetLanguage/$filename.ssp", values))
  }

}