scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "com.squareup.okhttp3" % "okhttp" % "4.3.1",
  "com.typesafe.akka" %% "akka-http" % "10.1.11",
  "com.typesafe.akka" %% "akka-stream" % "2.5.26",
  "org.asynchttpclient" % "async-http-client" % "2.10.4"
)

fork := true

javaOptions += "-DTOKEN=" + Option(System.getProperty("TOKEN")).getOrElse("")