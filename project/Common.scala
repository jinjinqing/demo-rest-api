import com.typesafe.sbt.SbtGit._
import sbt.Keys._
import sbt._
import sbtbuildinfo.Plugin._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

object Common {

  def taskProject(id: String, base: File) = Project(id = id, base = base)
    .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings)
    .settings(org.scalastyle.sbt.ScalastylePlugin.projectSettings)
    .settings(versionWithGit)
    .settings(buildInfoSettings)
    .settings(Common.settings)
    .settings(SbtScalariform.defaultScalariformSettings)
    .settings(
      mappings in (Compile, packageBin) ~= { _.filter(!_._1.getName.endsWith("application.conf")) },
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, git.gitHeadCommit, git.gitCurrentBranch),
      sourceGenerators in Compile <+= buildInfo,
      ScalariformKeys.preferences := ScalariformKeys.preferences.value
        .setPreference(AlignSingleLineCaseStatements, true)
        .setPreference(SpacesAroundMultiImports, false)
        .setPreference(DoubleIndentClassDeclaration, true),
      updateOptions := updateOptions.value.withCachedResolution(true)
    )

  lazy val scalastyleDuringTest = taskKey[Unit]("testScalastyle")
  lazy val settings = Seq(
    resolvers += "halarious" at "http://repo.surech.ch/content/repositories/hosted.halarious.releases",
    resolvers += "websudos" at "http://dl.bintray.com/websudos/oss-releases",
    scalaVersion := Version.scala,
    scalacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-deprecation",
      "-feature",
      "-unchecked"),
    fork := true,
    fork in Test := true,
    libraryDependencies ++= Dependencies.logging,
    //excludeDependencies ++= Seq("commons-logging" % "commons-logging", "org.slf4j" % "slf4j-log4j12"),
    organization := "demo",
    scalastyleDuringTest := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Compile).toTask("").value,
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oDF")
  )

}
