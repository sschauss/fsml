import de.sschauss.sle.fsml.exceptions._
import de.sschauss.sle.fsml.{FsmChecker, FsmlParser}
import org.specs2.mutable.Specification
import scala.io.Source

object FsmlTest extends Specification {

  val testResourceBaseDir = "src/test/resources"

  val parserError = s"$testResourceBaseDir/parserError.fsml"
  val initialNotOk = s"$testResourceBaseDir/initialNotOk.fsml"
  val idsNotOk = s"$testResourceBaseDir/idsNotOk.fsml"
  val resolutionNotOk = s"$testResourceBaseDir/resolutionNotOk.fsml"
  val determinismNotOk = s"$testResourceBaseDir/determinismNotOk.fsml"
  val reachabilityNotOk = s"$testResourceBaseDir/reachabilityNotOk.fsml"


  "The Fsm" should {
    "throw a ParseException for parserError.fsml" in {
      FsmlParser.parse(Source.fromFile(parserError)) must throwAn(new ParseException("string matching regex `\\z' expected but `i' found"))
    }
    "throw a InitialStateException for initialNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(initialNotOk))
      FsmChecker.fsmSingleInitial(fsm) must throwA(new InitialStateException)
    }
    "throw a DistinctIdException for idsNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(idsNotOk))
      FsmChecker.fsmDistinctIds(fsm) must throwA(new DistinctIdException)
    }
    "throw a ResolvableException for resolutionNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(resolutionNotOk))
      FsmChecker.fsmResolvable(fsm) must throwA(new ResolvableException)
    }
    "throw a DeterministicException for deterministicNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(determinismNotOk))
      FsmChecker.fsmDeterministic(fsm) must throwA(new DeterministicException)
    }
    "throw a ReachableException for reachabilityNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(reachabilityNotOk))
      FsmChecker.fsmReachable(fsm) must throwA(new ReachableException)
    }
  }

}