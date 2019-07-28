name := "sbt-init-aggregate"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / organization := "com.thezanzibarcompany.sbt"

ThisBuild / organizationName := "The Zanzibar Company Ltd."

publishArtifact := false

enablePlugins(SbtPlugin)

aggregateProjects(`sbt-init`)

val `sbt-init` =
  project.
    enablePlugins(SbtPlugin).
    settings(
      name := "sbt-init"
    )
