package com.thezanzibarcompany.sbt

import com.thezanzibarcompany.sbt.InitKeys._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import sbt.{Def, _}

import scala.language.implicitConversions

object InitPlugin extends AutoPlugin {

  override val trigger = allRequirements
  override val requires = JvmPlugin

  override def buildSettings: Seq[Def.Setting[_]] = Seq(
    buildProfile := { buildProfile ?? {
        sys.props.getOrElse("sbt.init.profile", "development") match {
          case s if s.equalsIgnoreCase("development") | s.equalsIgnoreCase("dev") => DevelopmentProfile
          case s if s.equalsIgnoreCase("release") | s.equalsIgnoreCase("rel") => ReleaseProfile
          case _ => sys.error("\"sbt.profile\" system property specified but cannot be matched to existing profile.")
        }} }.value
  )


  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    name ~= formalize,
    pomIncludeRepository := (_ => false),
  )

  object autoImport extends ScmUtil {

    val InitKeys = com.thezanzibarcompany.sbt.InitKeys

    implicit def toSettingKeyOps[A](settingKey: SettingKey[A]): SettingKeyOps[A] = SettingKeyOps(settingKey)


  }


  final case class SettingKeyOps[A](underlying: SettingKey[A]) {
    def fromRootProject = underlying := (LocalRootProject / underlying).value
  }


  // Used to formalize project name for projects declared with the syntax 'val fooProject = project ...'
  private def formalize(name: String): String = name.split("-|(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")
    .map(_.capitalize).mkString(" ")

}
