import Dependencies._

name := "kafka-streams-scala"

organization := "com.lightbend"

version := "1.1.1-flipp"

scalaVersion := Versions.Scala_2_12_Version

crossScalaVersions := Versions.CrossScalaVersions

scalacOptions := Seq("-Xexperimental", "-unchecked", "-deprecation", "-Ywarn-unused-import")

parallelExecution in Test := false

val flipplib = "http://flipplib.jfrog.io/flipplib/"

resolvers ++= Seq(
  Resolver.mavenLocal,
  "Flipplib Ext-Releases-Local" at flipplib + "ext-release-local",
  "FlippLib Snapshots" at flipplib + "libs-snapshot-local",
  "FlippLib Releases" at flipplib + "libs-release-local"
)

libraryDependencies ++= Seq(
  kafkaStreams excludeAll(ExclusionRule("org.slf4j", "slf4j-log4j12"), ExclusionRule("org.apache.zookeeper", "zookeeper")),
  scalaLogging % "test",
  logback % "test",
  kafka % "test" excludeAll(ExclusionRule("org.slf4j", "slf4j-log4j12"), ExclusionRule("org.apache.zookeeper", "zookeeper")),
  curator % "test",
  minitest % "test",
  minitestLaws % "test",
  algebird % "test",
  chill % "test",
  avro4s % "test"
)

testFrameworks += new TestFramework("minitest.runner.Framework")

licenses := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))

developers := List(
  Developer("debasishg", "Debasish Ghosh", "@debasishg", url("https://github.com/debasishg")),
  Developer("blublinsky", "Boris Lublinsky", "@blublinsky", url("https://github.com/blublinsky")),
  Developer("maasg", "Gerard Maas", "@maasg", url("https://github.com/maasg"))
)

organizationName := "lightbend"

organizationHomepage := Some(url("http://lightbend.com/"))

homepage := scmInfo.value map (_.browseUrl)

scmInfo := Some(ScmInfo(url("https://github.com/lightbend/kafka-streams-scala"), "git@github.com:lightbend/kafka-streams-scala.git"))

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishMavenStyle := true

publishArtifact in Test := true
