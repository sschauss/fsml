package de.sschauss.sle.fsml

package object exceptions {

  class ParseException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)

  class InitialStateException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)

  class DistinctIdException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)

  class ResolvableException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)

  class DeterministicException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)

  class ReachableException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)

  class IllegalInputException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)

}
