package com.test.lucene

import com.test.domain.Show
import com.test.json.JsonSupport
import com.test.provider.LuceneProvider
import org.apache.lucene.index.{DirectoryReader, Term}
import org.apache.lucene.search._
import org.json4s.jackson.Serialization.read

/**
 * Created by pmincz on 7/26/15.
 */
object Searcher extends LuceneProvider with JsonSupport {

  private def readSearcher(search: IndexSearcher => List[Show]) = {
    val indexReader = DirectoryReader.open(directory)
    val searcher = new IndexSearcher(indexReader)
    val data = search(searcher)
    indexReader.close()
    data
  }

  def search(indexFields: List[String], searchCriteria: String): List[Show] = {
    val query = new BooleanQuery()
    indexFields.foreach(field => query.add(new TermQuery(new Term(field, searchCriteria)), BooleanClause.Occur.SHOULD))

    readSearcher(indexSearcher => {
      val results = indexSearcher.search(query, 100)
      val docs = results.scoreDocs collect {
        case docId: ScoreDoc => read[Show](indexSearcher.doc(docId.doc).get("json"))
      }
      docs toList
    })
  }

}
