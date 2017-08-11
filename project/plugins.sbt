resolvers += Classpaths.typesafeReleases

resolvers += Classpaths.sbtPluginReleases

resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0-M4")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

libraryDependencies += "org.scalariform" %% "scalariform" % "0.1.8"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.6.4"