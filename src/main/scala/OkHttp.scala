import java.io.IOException

import okhttp3.{Call, Callback, OkHttpClient, Request, Response}

object OkHttp {

  def run(): Unit = {
    val client = new OkHttpClient()
    val getRequest = new Request.Builder()
      .url(Main.requestUri)
      .method("GET", null)
      .header("Authorization", s"Bearer ${Token.getToken}")
      .build()

    val headRequest = new Request.Builder()
      .url(Main.requestUri)
      .method("HEAD", null)
      .header("Authorization", s"Bearer ${Token.getToken}")
      .build()

    client
      .newCall(getRequest)
      .enqueue(new Callback {
        override def onFailure(call: Call, e: IOException): Unit =
          e.printStackTrace()
        override def onResponse(call: Call, firstResponse: Response): Unit = {
          println(firstResponse.code())
          println(firstResponse.headers())
          firstResponse.close()
          client
            .newCall(headRequest)
            .enqueue(new Callback {
              override def onFailure(call: Call, e: IOException): Unit =
                e.printStackTrace()
              override def onResponse(call: Call, secondResponse: Response): Unit = {
                println(secondResponse.code())
                println(secondResponse.headers())
                secondResponse.close()
                client
                  .newCall(getRequest)
                  .enqueue(new Callback {
                    override def onFailure(call: Call, e: IOException): Unit =
                      e.printStackTrace()
                    override def onResponse(call: Call, thirdResponse: Response): Unit = {
                      println(thirdResponse.code())
                      println(thirdResponse.headers())
                      thirdResponse.close()
                    }
                  })
              }
            })
        }
      })
  }

}
