package com.thezanzibarcompany.sbt

import com.thezanzibarcompany.sbt.InitKeys._
import sbt.Keys._
import sbt.{Def, _}

import scala.language.implicitConversions

object CompileOptionsPlugin extends AutoPlugin {

  override val trigger = allRequirements

  override val requires: Plugins = InitPlugin


  object autoImport {
    implicit def toProjectOps(project: Project): ProjectOps = ProjectOps(project)
  }

  final case class ProjectOps(underlying: Project) {
    def initScalacOpts: Project = underlying.settings(scalacOpts)
  }

  def scalacOpts: Def.Setting[Task[Seq[String]]] = Compile / compile / scalacOptions := {

    def baseOpts =  (Compile / compile / scalacOptions).value ++ Seq(
      Opts.compile.deprecation,
      Opts.compile.explaintypes,
      "-feature"
    )

    def productionOpts = baseOpts ++ Seq(
      "-opt:_"
    )

    def devOpts = productionOpts ++ Seq(
      Opts.compile.unchecked, "–Xlint:_", "-Xcheckinit"
    )

    if (buildProfile.value == DevelopmentProfile) devOpts else productionOpts
  }
}
