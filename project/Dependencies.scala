import sbt._

object Version {
  val scala       = "2.11.8"
  val scalaBinary = scala.substring(0,4)

  val scalaTest        = "2.2.6"
  val scalaz           = "7.1.8"
  val akka             = "2.3.14"
  val spray            = "1.3.3"
  val sprayJson        = "1.3.2"
  val swaggerSpray     = "0.7.2"
  val macwire          = "1.0.7"
  val typesafeConfig   = "1.3.0"
  val slick            = "3.2.0"
  val scalaLogging     = "3.1.0"
  val mysqlConnector   = "5.1.42"
  val sprayJsonLenses  = "0.6.1"
  val phantom          = "1.28.16"
  val json4sJackson    = "3.2.10"
  val logback          = "1.1.3"
}

object Library {
  val akkaActor      = "com.typesafe.akka"          %% "akka-actor"          % Version.akka
  val akkaSlf4j      = "com.typesafe.akka"          %% "akka-slf4j"          % Version.akka
  val akkaTestkit    = "com.typesafe.akka"          %% "akka-testkit"        % Version.akka
  val akkaCluster    = "com.typesafe.akka"          %% "akka-cluster"        % Version.akka
  val scalazCore     = "org.scalaz"                 %% "scalaz-core"         % Version.scalaz
  val scalazEffect   = "org.scalaz"                 %% "scalaz-effect"       % Version.scalaz
  val scalazTypelevel= "org.scalaz"                 %% "scalaz-typelevel"    % Version.scalaz
  val swaggerSpray   = "com.github.swagger-spray"   %% "swagger-spray"       % Version.swaggerSpray
  val macwireMacros  = "com.softwaremill.macwire"   %% "macros"              % Version.macwire
  val macwireRuntime = "com.softwaremill.macwire"   %% "runtime"             % Version.macwire
  val typesafeConfig = "com.typesafe"               %  "config"              % Version.typesafeConfig
  val scalaTest      = "org.scalatest"              %% "scalatest"           % Version.scalaTest
  val scalaLogging   = "com.typesafe.scala-logging" %% "scala-logging"       % Version.scalaLogging
  val sprayCan       = "io.spray"                   %% "spray-can"           % Version.spray
  val sprayRouting   = "io.spray"                   %% "spray-routing-shapeless2" % Version.spray
  val sprayClient    = "io.spray"                   %% "spray-client"        % Version.spray
  val sprayTestkit   = "io.spray"                   %% "spray-testkit"       % Version.spray
  val sprayJson      = "io.spray"                   %% "spray-json"          % Version.sprayJson
  val sprayJsonLenses= "net.virtual-void"           %% "json-lenses"         % Version.sprayJsonLenses
  val slick          = "com.typesafe.slick"         %% "slick"               % Version.slick
  val slickHikariCp  = "com.typesafe.slick"         %% "slick-hikaricp"      % Version.slick
  val mysqlConnector = "mysql"                      % "mysql-connector-java" % Version.mysqlConnector
  val scalaReflect   = "org.scala-lang"             %% "scala-reflect"       % Version.scala
  val phantom        = "com.websudos"               %% "phantom-dsl"         % Version.phantom
  val json4sJackson  = "org.json4s"                 %% "json4s-jackson"      % Version.json4sJackson
  val logback        = "ch.qos.logback"             % "logback-classic"      % Version.logback
}

object Dependencies {

  import Library._

  val logging = Seq(scalaLogging)

  val business = Seq(
    scalazCore, scalazEffect, scalazTypelevel
  )

  val rest = Seq(
    sprayCan, sprayRouting, sprayJson, sprayTestkit % "test",
    typesafeConfig,
    akkaActor, akkaSlf4j, akkaCluster,
    swaggerSpray,
    macwireMacros,
    macwireRuntime,
    sprayClient,
    scalaTest,
    sprayJsonLenses,
    logback
  )

  val mysql = Seq(
    scalaTest,
    slick, slickHikariCp,
    sprayJson,
    mysqlConnector
  )

  val cassandra = Seq(
    typesafeConfig,
    scalaTest,
    phantom,
    sprayJson
  )
}
