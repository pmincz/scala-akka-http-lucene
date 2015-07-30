package com.test.provider

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

/**
 * Created by pmincz on 7/12/15.
 */
object ActorSystemProperties {

  implicit val system = ActorSystem("akka-http-test")

  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  lazy val config = ConfigFactory.load()

}

trait ActorSystemProvider {

  implicit val system = ActorSystemProperties.system

  implicit val executionContext = ActorSystemProperties.executionContext
  implicit val materializer = ActorSystemProperties.materializer

  lazy val config = ActorSystemProperties.config

}
