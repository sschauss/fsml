#FSML -> Scala
##Requirements
- Java runtime v1.6 or later
- sbt v0.13

All other dependencies are handled by sbt:
- kiama v1.5.1
- scalate v1.6.1
- slf4j v1.7.5
- specs2 v2.3.4

#Usage

Just run `sbt` in the projects root folder.

In the sbt console you can execute the following non-standart commands (tab completion is supported):
- `generate <file> [--language <language>] [--input <inputs>]`

All generated code will be written to `src-gen/main/<language>`.

#Tests

In the sbt console execute the command `test`.
