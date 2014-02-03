package de.sschauss.sle.fsml.exceptions

class ReachableException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)
