object Main extends App {
  lazy val requestUri = args(0)

  println("Running okhttp")
  OkHttp.run()

  println("running akka-http")
  AkkaHttp.run()

  println("running async-http client")
  AsyncHttpClient.run()

}
