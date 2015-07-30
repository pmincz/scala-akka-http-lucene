package com.test

import scala.collection.JavaConversions._
import scala.concurrent.Future

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.test.client.ShowClient
import com.test.json.JsonSupport
import com.test.lucene.{Indexer, Searcher}
import com.test.provider.ActorSystemProvider

/**
 * Created by pmincz on 7/8/15.
 */
object Boot extends App with ActorSystemProvider with JsonSupport {

  lazy val fields = config.getStringList("lucene.index.fields") toList

  lazy val route =
    get {
      path("index") {
        complete {
          ShowClient.getShows map(Indexer.index(_, fields))
        }
      } ~
        path("search") {
          parameters('q.as[String]) { q =>
            complete {
              Future {
                Searcher.search(fields, q.toLowerCase)
              }
            }
          }
        }
      }

  Http(system).bindAndHandle(route, interface = config.getString("service.host"), port = config.getInt("service.port"))

}
