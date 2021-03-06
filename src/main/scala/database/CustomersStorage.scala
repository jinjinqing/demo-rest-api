package database

import java.util.UUID

import database.tables.Customers
import services.customer.{ NewCustomer, CustomerFinder, CustomerCreator, Customer }
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._
import slick.dbio.Effect.Write

import scala.util.Try

class CustomersStorage
    extends TableQuery(new Customers(_))
    with CustomerFinder
    with CustomerCreator
    with DatabaseHelper {

  override def findAll: List[Customer] = {
    futureToResult(db.run(this.result)).toList
  }

  override def findById(id: UUID): Option[Customer] = {
    futureToResult(db.run(this.findBy(_.id).applied(id).result)).headOption
  }

  def exists(id: UUID) = {
    futureToResult(db.run(this.findBy(_.id).applied(id).map(_.id).exists.result))
  }

  private def creationSeq(guid: UUID, newCustomer: NewCustomer): DBIOAction[Unit, NoStream, Write] = {
    DBIO.seq(
      sqlu"LOCK TABLES CUSTOMERS WRITE",
      this += Customer(guid, newCustomer.firstName, newCustomer.lastName),
      sqlu"UNLOCK TABLES"
    )
  }

  override def create(newCustomer: NewCustomer): Either[Throwable, UUID] = {
    Try {
      val guid = java.util.UUID.randomUUID
      futureToResult(db.run(creationSeq(guid, newCustomer)))
      guid
    } match {
      case scala.util.Success(id) => Right(id)
      case scala.util.Failure(f) => Left(new Exception(f))
    }
  }

}

object customerStorage extends CustomersStorage
