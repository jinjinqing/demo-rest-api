package database

import java.util.UUID

import database.tables.Payments
import services.payment.{ NewPayment, Payment, PaymentCreator, PaymentFinder }
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._
import slick.dbio.Effect.Write

import scala.util.Try

class PaymentsStorage
    extends TableQuery(new Payments(_))
    with PaymentFinder
    with PaymentCreator
    with DatabaseHelper {

  override def findAll: List[Payment] = {
    futureToResult(db.run(this.result)).toList
  }

  override def findById(id: UUID): Option[Payment] = {
    futureToResult(db.run(this.findBy(_.id).applied(id).result)).headOption
  }

  def exists(id: UUID) = {
    futureToResult(db.run(this.findBy(_.id).applied(id).map(_.id).exists.result))
  }

  def findByInvoiceId(id: UUID): List[Payment] = {
    futureToResult(db.run(this.findBy(_.invoiceId).applied(id).result)).toList
  }

  private def creationSeq(guid: UUID, newPayment: NewPayment): DBIOAction[Unit, NoStream, Write] = {
    DBIO.seq(
      sqlu"LOCK TABLES PAYMENTS WRITE",
      this += Payment(guid, newPayment.invoiceId, BigDecimal(newPayment.value)),
      sqlu"UNLOCK TABLES"
    )
  }

  override def create(newPayment: NewPayment): Either[Throwable, UUID] = {
    Try {
      val guid = java.util.UUID.randomUUID
      futureToResult(db.run(creationSeq(guid, newPayment)))
      guid
    } match {
      case scala.util.Success(id) => Right(id)
      case scala.util.Failure(f) => Left(new Exception(f))
    }
  }
}

object paymentStorage extends PaymentsStorage
