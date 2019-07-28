package com.thezanzibarcompany.sbt

import sbt._

object InitKeys {

  val buildProfile = settingKey[BuildProfile]("'BuildProfile' used to determine build specific settings.")

  sealed trait BuildProfile
  case object ReleaseProfile extends BuildProfile
  case object DevelopmentProfile extends BuildProfile

}
