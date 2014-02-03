package de.sschauss.sle.fsml.exceptions

class ResolvableException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)
