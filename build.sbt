name := "Scalate"

organization := "org.scalatra.scalate"

version := "1.7.1-SNAPSHOT"

scalaVersion := crossScalaVersions.value.head

crossScalaVersions := Seq("2.10.4", "2.11.5")

javaVersionPrefix in javaVersionCheck := Some("1.7")

startYear := Some(2010)

licenses += "The Apache Software License, Version 2.0" → url("http://www.apache.org/licenses/LICENSE-2.0")

scmInfo := Some(ScmInfo(url("http://github.com/scalate/scalate"),
  "scm:git:git://github.com/scalate/scalate.git", Some("scm:git:ssh://git@github.com:scalate/scalate.git")))

homepage := Some(url("http://scalate.github.io/scalate"))

unidocOpts(filter = scalateJrebel, scalateWar, scalateWeb)

lazy val scalateUtil = scalateProject("util")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(junit % Test, logbackClassic % Test, scalaTest % Test, slf4jApi)
  .settings(
    description := "Scalate Utilities.",
    parallelExecution in Test := false,
    addScalaModules(11, scalaXml, scalaParserCombinators),
    unmanagedSourceDirectories in Test += (sourceDirectory in Test).value / s"scala_${scalaBinaryVersion.value}")

lazy val scalateCore = scalateProject("core")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateUtil)
  .dependsOn(
    javaxServlet % Optional,
    logbackClassic % "runtime,optional",
    jerseyCore % Optional,
    jerseyServer % Optional,
    osgiCore % "provided,optional",
    rhinoCoffeeScript % Optional,
    scalamd % Optional,
    scalaTest % Test,
    junit % Test)
  .settings(
    description := "Scalate Core",
    libraryDependencies += scalaCompiler(scalaOrganization.value, scalaVersion.value),
    OsgiKeys.privatePackage := Seq("org.fusesource.scalate"),
    unmanagedSourceDirectories in Compile += (sourceDirectory in Compile).value / s"scala_${scalaBinaryVersion.value}")

lazy val scalateTest = scalateProject("test")
  .scalateSettings
  .published
  .dependsOn(scalateCore)
  .dependsOn(jettyServer, jettyWebapp, jettyUtil, scalaTest, junit, seleniumDriver)
  .settings(description := "Scalate Test Support Classes.")

lazy val scalateCamel = scalateProject("camel")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore, scalateTest % Test)
  .settings(
    description := "Camel component for Scalate.",
    addScalaDependentDeps(10 → camelScala213, 10 → camelSpring213, 11 → camelScala214, 11 → camelSpring214))

lazy val scalateGuice = scalateProject("guice")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore)
  .dependsOn(
    atmosphereJersey % Provided,
    javaxServlet,
    jerseyCore,
    jerseyGuice,
    scalaTest % Test,
    junit % Test,
    logbackClassic % Test)
  .settings(description := "Guice integration for a Jersey based Scalate web application.")

lazy val scalateJrebel = scalateProject("jrebel")
  .scalateSettings
  .published
  .dependsOn(scalateCore)
  .dependsOn(jRebelSDK % Provided)
  .settings(description := "JRebel plugin for reloading Scalate templates on class reload.")

lazy val scalateJruby = scalateProject("jruby")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore, scalateTest % Test)
  .dependsOn(jRubyComplete, logbackClassic % Test)
  .settings(description := "Scalate integration with JRuby including Ruby based filters such as sass.")

lazy val scalateJspConverter = scalateProject("jsp-converter")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore)
  .dependsOn(karafShell, scalaTest % Test, junit % Test, logbackClassic % Test)
  .settings(
    description := "Converter for JSP to SSP",
    resolvers += fuseSourceMavenRepository,
    OsgiKeys.privatePackage := Seq("org.fusesource.scalate.converter"))

lazy val scalateLess = scalateProject("less")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore, scalateTest % Test)
  .dependsOn(lessCssEngine, logbackClassic % Test)
  .settings(
    description := "Scalate LESS filter.",
    OsgiKeys.bundleSymbolicName := "org.scalatra.scalate.filter.less",
    OsgiKeys.privatePackage := Seq("org.fusesource.scalate.filter.less"))

lazy val scalateMarkdownJ = scalateProject("markdownj")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore, scalateTest % Test)
  .dependsOn(markdownJ, scalaTest % Test, junit % Test, logbackClassic % Test)
  .settings(
    description := "Scalate MarkdownJ filter.",
    OsgiKeys.bundleSymbolicName := "org.scalatra.scalate.filter.markdownj",
    OsgiKeys.privatePackage := Seq("org.fusesource.scalate.filter.markdownj"))

lazy val scalatePage = scalateProject("page")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore, scalateWikitext, scalateTest % Test)
  .dependsOn(rhinoCoffeeScript, scalamd, snakeYaml)
  .settings(description := "Scalate multipart page filter (similar to Webgen page format).")

lazy val scalatePegdown = scalateProject("pegdown")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore, scalateTest % Test)
  .dependsOn(pegdown)
  .settings(
    description := "Scalate Pegdown filter.",
    OsgiKeys.bundleSymbolicName := "org.scalatra.scalate.filter.pegdown",
    OsgiKeys.privatePackage := Seq("org.fusesource.scalate.filter.pegdown"))


lazy val scalateSpringMVC = scalateProject("spring-mvc")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore)
  .dependsOn(javaxServlet % Provided, springMVC, scalaTest % Test, junit % Test)
  .settings(
    description := "Scalate Spring MVC integration.",
    OsgiKeys.privatePackage := Seq("org.fusesource.scalate.spring.view"))

lazy val scalateTool = scalateProject("tool")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore)
  .dependsOn(karafShell, confluenceSoap, axis, axisWsdl, jTidy)
  .settings(
    description := "Scalate Command Line Tool.",
    resolvers += fuseSourceMavenRepository)

lazy val scalateWar = scalateProject("war")
  .scalateSettings
  .published
  .dependsOn(scalateWeb, scalateTest % Test)
  .dependsOn(logbackClassic, jerseyServer, jerseyCore)
  .settings(tomcat(port = 8087, args = Seq("scalate.mode=dev")): _*)
  .settings(
    description := "Scalate Base Web Application",
    parallelExecution in Test := false,
    unmanagedResourceDirectories in Test += baseDirectory.value / "src/main/webapp")

lazy val scalateWeb = scalateProject("web")
  .scalateSettings
  .published
  .dependsOn(scalatePage, scalateTest % Test)
  .dependsOn(javaxServlet % Provided)
  .settings(description := "Single dependency for all modules required to use Scalate and common wiki formats.")

lazy val scalateWikitext = scalateProject("wikitext")
  .scalateSettings
  .osgiSettings
  .published
  .dependsOn(scalateCore, scalateTest % Test)
  .dependsOn(wikitextConfluence, wikitextTextile, logbackClassic % Test)
  .settings(description := "Scalate WikiText integration for Markdown and Confluence notations.")
