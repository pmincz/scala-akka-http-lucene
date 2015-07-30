package com.test

import com.test.service.ShowService

import scala.concurrent.Future

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.test.json.JsonSupport
import com.test.provider.ActorSystemProvider

/**
 * Created by pmincz on 7/8/15.
 */
object Boot extends App with ActorSystemProvider with JsonSupport {

  lazy val route =
    get {
      path("index") {
        complete {
          ShowService.indexShows
        }
      } ~
        path("search") {
          parameters('q.as[String]) { q =>
            complete {
              Future {
                ShowService.searchShows(q)
              }
            }
          }
        }
      }

  Http(system).bindAndHandle(route, interface = config.getString("service.host"), port = config.getInt("service.port"))

}
