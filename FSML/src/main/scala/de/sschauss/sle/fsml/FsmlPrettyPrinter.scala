package de.sschauss.sle.fsml

import org.kiama.output.PrettyPrinter
import de.sschauss.sle.fsml.FsmlAst._

object FsmlPrettyPrinter extends PrettyPrinter {

  def pretty(node: FsmlNode): String =
    super.pretty(show(node))

  def show(node: FsmlNode): Doc =
    node match {
      case Fsm(states) => ssep(states map show, line)
      case State(true, id, transitions) => "initial state " <> id <+> "{" <> nest(line <> vsep(transitions map show)) <@> "}"
      case State(false, id, transitions) => "state " <> id <+> "{" <> nest(line <> vsep(transitions map show)) <@> "}"
      case Transition(input, Some(action), Some(id)) => input <> "/" <> action <> " -> " <> id <> ";"
      case Transition(input, Some(action), None) => input <> "/" <> action <> ";"
      case Transition(input, None, Some(id)) => input <+> "-> " <> id <> ";"
      case Transition(input, None, None) => input <> ";"
    }

}