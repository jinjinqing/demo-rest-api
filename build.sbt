import Common.taskProject

scalacOptions ++= Seq("-encoding", "UTF-8")

lazy val core = taskProject(id = "business", base = file("business"))
  .settings(buildInfoPackage := "demo.business")
  .settings(
    libraryDependencies ++= Dependencies.business
  )

lazy val mysql = taskProject(id = "storage", base = file("storage"))
  .settings(buildInfoPackage := "demo.storage.mysql")
  .settings(libraryDependencies ++= Dependencies.mysql)
  .dependsOn(core)

lazy val rest = taskProject(id = "rest", base = file("rest"))
  .settings(Revolver.settings: _*)
  .settings(buildInfoPackage := "demo.rest")
  .settings(
    libraryDependencies ++= Dependencies.rest,
    Revolver.reStart <<= Revolver.reStart,
    scalacOptions += "-Xmacro-settings:conf.output.dir=" + baseDirectory.value / "src/main/resources/",
    scalacOptions += "-language:reflectiveCalls"
  ).dependsOn(core, mysql)



