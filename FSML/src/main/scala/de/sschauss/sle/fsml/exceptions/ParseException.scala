package de.sschauss.sle.fsml.exceptions

class ParseException(val message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)
