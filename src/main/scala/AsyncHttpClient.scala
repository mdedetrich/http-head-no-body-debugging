import org.asynchttpclient.Dsl.asyncHttpClient
import org.asynchttpclient.{RequestBuilder, Response}
import org.asynchttpclient.uri.Uri

object AsyncHttpClient {
  def run(): Unit = {

    val getRequest = new RequestBuilder()
      .setUri(Uri.create(Main.requestUri))
      .setMethod("GET")
      .addHeader("Authorization", s"Bearer ${Token.getToken}")
      .build()

    val headRequest = new RequestBuilder()
      .setUri(Uri.create(Main.requestUri))
      .setMethod("HEAD")
      .addHeader("Authorization", s"Bearer ${Token.getToken}")
      .build()

    val client = asyncHttpClient()

    val future = client.executeRequest(getRequest).toCompletableFuture.thenCompose { firstResponse: Response =>
      println(firstResponse.getStatusCode)
      println(firstResponse.getHeaders.toString)
      client.executeRequest(headRequest).toCompletableFuture.thenCompose { secondResponse: Response =>
        println(secondResponse.getStatusCode)
        println(secondResponse.getHeaders.toString)
        client.executeRequest(getRequest).toCompletableFuture.thenApply { thirdResponse: Response =>
          println(thirdResponse.getStatusCode)
          println(thirdResponse.getHeaders.toString)
          thirdResponse
        }
      }
    }

    future.get()
  }
}
