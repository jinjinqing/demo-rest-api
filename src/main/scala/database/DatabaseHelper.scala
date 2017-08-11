package database

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._

trait DatabaseHelper {
  lazy val db = Database.forConfig("database.mysql")

  implicit def futureToResult[T](future: Future[T]) = {
    Await.result(future, 60 minutes)
  }

  def dataBaseInit = {
    val schemas = customerStorage.schema ++ invoiceStorage.schema ++ paymentStorage.schema
    val operation = schemas.create
    db.run(operation)
  }
}

object DatabaseHelper extends DatabaseHelper