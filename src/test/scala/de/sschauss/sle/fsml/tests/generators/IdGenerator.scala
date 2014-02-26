package de.sschauss.sle.fsml.tests.generators

import org.scalacheck.Gen
import de.sschauss.sle.fsml.Ast._

object IdGenerator {

  def id: Gen[Name] = Gen.identifier

  def ids: Gen[Set[Name]] = for {
    ids <- Gen.containerOf[Set, Name](id)
  } yield ids


}
