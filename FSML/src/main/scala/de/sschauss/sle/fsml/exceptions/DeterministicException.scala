package de.sschauss.sle.fsml.exceptions

class DeterministicException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)
