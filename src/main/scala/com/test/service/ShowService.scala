package com.test.service

import scala.collection.JavaConversions._

import com.test.client.ShowClient
import com.test.domain.Show
import com.test.lucene.{Indexer, Searcher}
import com.test.lucene.Searcher._
import com.test.provider.ActorSystemProvider
import org.apache.lucene.index.DirectoryReader

/**
 * Created by pmincz on 7/29/15.
 */
object ShowService extends ActorSystemProvider {

  lazy val fields = config.getStringList("lucene.index.fields") toList

  def indexShows = {
    ShowClient.getShows map(Indexer.index(_, fields))
  }

  def searchShows(query: String): List[Show] = {
    DirectoryReader.indexExists(directory) match {
      case true => Searcher.search(fields, query.toLowerCase)
      case false => indexShows;searchShows(query)
    }
  }

}
