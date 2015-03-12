lazy val plugins = (project in file("."))
  .dependsOn(sbtOsgi)

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "1.0.0")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-javaversioncheck" % "0.1.0")

addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.3.2")

//addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.1.6") TODO: What is our Binary compatibility policy?

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")

lazy val sbtOsgi = uri("git://github.com/arashi01/sbt-osgi.git#bd07211")
