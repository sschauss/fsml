package de.sschauss.sle.fsml.exceptions

class IllegalInputException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)
