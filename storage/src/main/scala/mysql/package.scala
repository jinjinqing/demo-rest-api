package demo.storage

import mysql.customerStorage

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

package object mysql {

  val db = Database.forConfig("demo.storage.mysql")

  object customerStorage extends CustomersStorage

  implicit def futureToResult[T](future: Future[T]) = {
    Await.result(future, 60 minutes)
  }
}

object DemoDatabase {
  lazy val schemas = customerStorage.schema

  def init = {
    val db = Database.forConfig("demo.storage.mysql")
    val operation = schemas.create
    db.run(operation)
  }

}
