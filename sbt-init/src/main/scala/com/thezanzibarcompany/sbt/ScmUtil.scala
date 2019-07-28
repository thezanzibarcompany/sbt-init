package com.thezanzibarcompany.sbt

import sbt.librarymanagement.ScmInfo
import sbt.url

trait ScmUtil {

  final case class AzureTFVC(project: String, owner: String) extends ScmRepository {
    override def browseUrl: String = s"https://dev.azure.com/$owner/$project/_versionControl"
    override def connection: String = browseUrl
    override def developerConnection: String = browseUrl
    override def `type`: ScmType = TFVC
  }

  final case class AzureGitRepository(repo: String, project: String, owner: String) extends ScmRepository {
    override def browseUrl: String = s"https://dev.azure.com/$owner/$project/_git/$repo"
    override def connection: String = s"https://$owner@dev.azure.com/$owner/$project/_git/$repo"
    override def developerConnection: String = s"git@ssh.dev.azure.com:v3/$owner/$project/$repo"
    override def `type`: ScmType = GitScm
  }

  object AzureGitRepository {
    def apply(project: String, owner: String): AzureGitRepository = new AzureGitRepository(project, project, owner)
  }

  final case class GithubRepository(repo: String, owner: String) extends ScmRepository {
    def browseUrl = s"https://github.com/$owner/$repo"
    def connection = s"scm:git:$browseUrl.git"
    def developerConnection = s"scm:git:git@github.com:$owner/$repo.git"
    override def `type`: ScmType = GitScm
  }

  sealed abstract class ScmRepository {
    def browseUrl: String
    def connection: String
    def developerConnection: String
    def `type`: ScmType
    def scmInfo: ScmInfo = ScmInfo(url(browseUrl), connection, Some(developerConnection))
  }

  sealed trait ScmType
  case object GitScm extends ScmType
  case object TFVC extends ScmType

}
