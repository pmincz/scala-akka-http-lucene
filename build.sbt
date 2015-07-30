name := "scala-akkahttp-lucene"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "Spray repository" at "http://repo.spray.io",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= {
  val akka = "2.3.11"
  val akkaHttp = "1.0"
  val lucene = "5.2.1"
  Seq(
    "com.typesafe.akka" %% "akka-http-experimental"       % akkaHttp,
    "de.heikoseeberger" %% "akka-http-json4s"             % "0.9.1",
    "org.json4s"        %% "json4s-jackson"               % "3.2.11",
    "com.typesafe.akka" %% "akka-slf4j"                   % akka,
    "org.apache.lucene" %  "lucene-core"                  % lucene,
    "org.apache.lucene" %  "lucene-analyzers-common"      % lucene,
    "org.apache.lucene" %  "lucene-queries"               % lucene,
    "org.apache.lucene" %  "lucene-facet"                 % lucene,
    "org.apache.lucene" %  "lucene-spatial"               % lucene,
    "org.apache.lucene" %  "lucene-queryparser"           % lucene
  )
}