package demo.storage

import demo.storage.mysql.{invoiceStorage, customerStorage}

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

package object mysql {

  val db = Database.forConfig("demo.storage.mysql")

  object customerStorage extends CustomersStorage
  object invoiceStorage extends InvoicesStorage

  implicit def futureToResult[T](future: Future[T]) = {
    Await.result(future, 60 minutes)
  }
}

object DemoDatabase {
  lazy val schemas = customerStorage.schema ++ invoiceStorage.schema

  def init = {
    val db = Database.forConfig("demo.storage.mysql")
    val operation = schemas.create
    db.run(operation)
  }

}
