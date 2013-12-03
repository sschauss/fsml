package de.sschauss.sle

import scala.util.parsing.combinator._
import scala.reflect.runtime.universe.{ reify, showRaw }
import scala.util.parsing.input.Reader
import scala.util.parsing.input.CharArrayReader
import scala.io.Source
import java.io.File

object FSMLParser extends JavaTokenParsers {

  def fsm: Parser[Any] = rep(state)

  def state: Parser[Any] = initial ~ "state" ~ id ~ "{" ~ rep(transition) ~ "}"

  def initial: Parser[Any] = "initial" | ""

  def transition: Parser[Any] = input ~ opt("/" ~ action) ~ opt("->" ~ id) ~ ";"

  def id: Parser[Any] = "[A-Za-z0-9]*".r

  def input: Parser[Any] = "[A-Za-z0-9]*".r

  def action: Parser[Any] = "[A-Za-z0-9]*".r

}

object Runner {

  def main(args: Array[String]) {
    val fsmlParser = FSMLParser
    val input = Source.fromFile(args(0)).mkString
    println(fsmlParser.parseAll(fsmlParser.fsm, input))
  }

}