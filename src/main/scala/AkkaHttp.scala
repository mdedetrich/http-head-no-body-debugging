import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, Uri}
import akka.http.scaladsl.{Http, HttpExt}
import akka.stream.ActorMaterializer

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.language.postfixOps

object AkkaHttp {
  implicit val system: ActorSystem                = ActorSystem()
  implicit val http: HttpExt                      = Http()
  implicit val materializer: ActorMaterializer    = ActorMaterializer()
  implicit val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.global

  val getRequest = HttpRequest(HttpMethods.GET,
                               Uri(Main.requestUri),
                               List(
                                 RawHeader("Authorization", s"Bearer ${Token.getToken}")
                               ))
  val headRequest = HttpRequest(HttpMethods.HEAD,
                                Uri(Main.requestUri),
                                List(
                                  RawHeader("Authorization", s"Bearer ${Token.getToken}")
                                ))

  def run(): Unit = {
    def future() =
      for {
        firstResponse <- http.singleRequest(getRequest)
        _             = firstResponse.discardEntityBytes()
        _ = {
          println(firstResponse.status.intValue())
          println(firstResponse.headers.mkString("", "\n", "\n"))
        }
        secondResponse <- http.singleRequest(headRequest)
        _              = secondResponse.discardEntityBytes()
        _ = {
          println(secondResponse.status.intValue())
          println(secondResponse.headers.mkString("", "\n", "\n"))
        }
        thirdResponse <- http.singleRequest(getRequest)
        _             = thirdResponse.discardEntityBytes()
        _ = {
          println(thirdResponse.status.intValue())
          println(thirdResponse.headers.mkString("", "\n", "\n"))
        }
      } yield ()

    val f = Future.sequence(
      List.fill(3)(future())
    )
    Await.result(f, 1 minute)
  }
}
