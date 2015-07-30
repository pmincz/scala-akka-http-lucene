package com.test.client

import scala.concurrent.Future

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.test.domain.{Show, ShowResult}
import com.test.json.JsonSupport
import com.test.provider.ActorSystemProvider

/**
 * Created by pmincz on 7/27/15.
 */
object ShowClient extends ActorSystemProvider with JsonSupport {

  lazy val key = config.getString("client.key")
  lazy val domain = config.getString("client.url")

  def getShows: Future[List[Show]] = {
    val url = s"${domain}/discover/tv?sort_by=popularity.desc&api_key=${key}"

    Http().singleRequest(HttpRequest(uri = url)).flatMap { r =>
      Unmarshal(r.entity).to[ShowResult]
    }.map(_.results)
  }

}
