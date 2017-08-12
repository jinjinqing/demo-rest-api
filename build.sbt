name := "demo-rest-api"

organization := "demo"

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-feature",
  "-unchecked"
)

val scala            = "2.11.8"
val scalaTest        = "2.2.6"
val scalaMock        = "3.2.2"
val akka             = "2.3.14"
val spray            = "1.3.3"
val sprayJson        = "1.3.2"
val swaggerSpray     = "0.7.2"
val typesafeConfig   = "1.3.0"
val slick            = "3.2.0"
val scalaLogging     = "3.1.0"
val mysqlConnector   = "5.1.42"
val sprayJsonLenses  = "0.6.1"
val phantom          = "1.28.16"
val json4sJackson    = "3.2.10"
val logback          = "1.1.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka"          %% "akka-slf4j"          % akka,
  "com.typesafe.akka"          %% "akka-testkit"        % akka,
  "com.typesafe.akka"          %% "akka-cluster"        % akka,
  "com.github.swagger-spray"   %% "swagger-spray"       % swaggerSpray,
  "com.typesafe"               %  "config"              % typesafeConfig,
  "org.scalatest"              %% "scalatest"           % scalaTest,
  "org.scalamock"              %% "scalamock-scalatest-support" % scalaMock,
  "com.typesafe.scala-logging" %% "scala-logging"       % scalaLogging,
  "io.spray"                   %% "spray-can"           % spray,
  "io.spray"                   %% "spray-routing-shapeless2" % spray,
  "io.spray"                   %% "spray-client"        % spray,
  "io.spray"                   %% "spray-testkit"       % spray,
  "io.spray"                   %% "spray-json"          % sprayJson,
  "com.typesafe.slick"         %% "slick"               % slick,
  "com.typesafe.slick"         %% "slick-hikaricp"      % slick,
  "mysql"                      % "mysql-connector-java" % mysqlConnector,
//  "com.websudos"               %% "phantom-dsl"         % phantom,
  "org.json4s"                 %% "json4s-jackson"      % json4sJackson,
  "ch.qos.logback"             % "logback-classic"      % logback
)

//resolvers += "websudos" at "http://dl.bintray.com/websudos/oss-releases"
//resolvers += "OSS Sonatype" at "https://repo1.maven.org/maven2/"

Seq(Revolver.settings: _*)

net.virtualvoid.sbt.graph.Plugin.graphSettings
