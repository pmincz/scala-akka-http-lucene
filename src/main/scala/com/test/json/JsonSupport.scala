package com.test.json

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{DefaultFormats, Formats, Serialization, jackson}

/**
 * Created by pmincz on 7/12/15.
 */
trait JsonSupport extends Json4sSupport {

  implicit val formats: Formats = DefaultFormats
  implicit val jacksonSerialization: Serialization = jackson.Serialization

}