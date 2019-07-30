name := "sbt-init-aggregate"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / organization := "com.thezanzibarcompany.sbt"

ThisBuild / organizationName := "The Zanzibar Company Ltd."

ThisBuild / publishTo := Some {
  if (version.value.contains("SNAPSHOT"))
    "repo-thezanzibarcompany-sbt-snapshot" at "https://repo.thezanzibarcompany.com/repository/sbt-plugins-snapshot"
  else "repo-thezanzibarcompany-sbt-release" at "https://repo.thezanzibarcompany.com/repository/sbt-plugins"
}


ThisBuild / publishMavenStyle := true

publishArtifact := false

enablePlugins(SbtPlugin)

aggregateProjects(`sbt-init`)

val `sbt-init` =
  project.
    enablePlugins(SbtPlugin).
    settings(
      name := "sbt-init"
    )
