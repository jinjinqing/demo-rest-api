package database

import java.util.UUID

import database.tables.Invoices
import services.invoice.{ NewInvoice, Invoice, InvoiceCreator, InvoiceFinder }
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._
import slick.dbio.Effect.Write

import scala.util.Try

class InvoicesStorage
    extends TableQuery(new Invoices(_))
    with InvoiceFinder
    with InvoiceCreator
    with DatabaseHelper {

  override def findAll: List[Invoice] = {
    futureToResult(db.run(this.result)).toList
  }

  override def findById(id: UUID): Option[Invoice] = {
    futureToResult(db.run(this.findBy(_.id).applied(id).result)).headOption
  }

  def exists(id: UUID) = {
    futureToResult(db.run(this.findBy(_.id).applied(id).map(_.id).exists.result))
  }

  def findByCustomerId(id: UUID): List[Invoice] = {
    futureToResult(db.run(this.findBy(_.customerId).applied(id).result)).toList
  }

  private def creationSeq(guid: UUID, newInvoice: NewInvoice): DBIOAction[Unit, NoStream, Write] = {
    DBIO.seq(
      sqlu"LOCK TABLES INVOICES WRITE",
      this += Invoice(guid, newInvoice.customerId, newInvoice.invoiceDate, newInvoice.chargeName, newInvoice.toBePaid),
      sqlu"UNLOCK TABLES"
    )
  }

  override def create(newInvoice: NewInvoice): Either[Throwable, UUID] = {
    Try {
      val guid = java.util.UUID.randomUUID
      futureToResult(db.run(creationSeq(guid, newInvoice)))
      guid
    } match {
      case scala.util.Success(id) => Right(id)
      case scala.util.Failure(f) => Left(new Exception(f))
    }
  }
}

object invoiceStorage extends InvoicesStorage