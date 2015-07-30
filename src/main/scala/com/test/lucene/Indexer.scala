package com.test.lucene

import scala.collection.mutable.ListBuffer

import com.test.domain.Show
import com.test.json.JsonSupport
import com.test.provider.LuceneProvider
import org.apache.lucene.document.{Document, Field, StoredField, TextField}
import org.apache.lucene.index.Term
import org.json4s.jackson.Serialization.write

/**
 * Created by pmincz on 7/26/15.
 */
object Indexer extends LuceneProvider with JsonSupport {

  val indexedIds = new ListBuffer[Long]()

  def getValue(x: Any, field: String): String = {
    val methodName = x.getClass.getMethod(field)
    methodName.invoke(x).toString
  }

  def getDocument(show: Show, indexFields: List[String]): Document = {
    val doc = new Document()
    val jsString = write(show)
    indexFields.foreach(field => doc.add(new TextField(field, getValue(show, field), Field.Store.YES)))
    doc.add(new StoredField("json", jsString))
    doc
  }

  def index(shows: List[Show], indexFields: List[String]): Unit = {
    writer.withSession { writer =>
      shows.foreach { show =>
        val doc = getDocument(show, indexFields)
        indexedIds.contains(show.id) match {
          case true => writer.updateDocument(new Term("id", show.id.toString), doc)
          case false => {
            indexedIds += show.id
            writer.addDocument(doc)
          }
        }
      }
    }
  }

}
