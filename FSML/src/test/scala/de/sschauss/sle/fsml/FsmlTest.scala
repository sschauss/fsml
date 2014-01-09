import de.sschauss.sle.fsml.{FsmChecker, FsmlParser}
import org.specs2.mutable.Specification
import scala.io.Source

object FsmlTest extends Specification {

  val testResourceBaseDir = "src/test/resources"

  val initialNotOk = s"$testResourceBaseDir/initialNotOk.fsml"
  val idsNotOk = s"$testResourceBaseDir/idsNotOk.fsml"
  val resolutionNotOk = s"$testResourceBaseDir/resolutionNotOk.fsml"
  val determinismNotOk = s"$testResourceBaseDir/determinismNotOk.fsml"
  val reachabilityNotOk = s"$testResourceBaseDir/reachabilityNotOk.fsml"

  "The Fsm" should {
    "throw a InitialStateException for initialNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(initialNotOk).mkString)
      FsmChecker.fsmSingleInitial(fsm) must throwA(new InitialStateException)
    }
    "throw a DistinctIdException for idsNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(idsNotOk).mkString)
      FsmChecker.fsmDistinctIds(fsm) must throwA(new DistinctIdException)
    }
    "throw a ResolvableException for resolutionNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(resolutionNotOk).mkString)
      FsmChecker.fsmResolvable(fsm) must throwA(new ResolvableException)
    }
    "throw a DeterministicException for deterministicNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(determinismNotOk).mkString)
      FsmChecker.fsmDeterministic(fsm) must throwA(new DeterministicException)
    }
    "throw a ReachableException for reachabilityNotOk.fsml" in {
      val fsm = FsmlParser.parse(Source.fromFile(reachabilityNotOk).mkString)
      FsmChecker.fsmReachable(fsm) must throwA(new ReachableException)
    }
  }

}