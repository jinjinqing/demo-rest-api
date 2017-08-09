package demo.storage.mysql.tables

import java.util.UUID

import demo.business.payment.boundary.Payment
import demo.storage.mysql.invoiceStorage
import slick.jdbc.MySQLProfile.api._

class Payments(tag: Tag) extends Table[Payment](tag, "PAYMENTS") {
  def id = column[UUID]("ID", O.PrimaryKey)
  def invoiceId = column[UUID]("INVOICE_ID")
  def value = column[String]("VALUE")

  def invoice = foreignKey(
    "PAYMENTS_INVOICE_ID_FK",
    invoiceId, invoiceStorage
  )(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

  override def * = (id, invoiceId, value) <> (Payment.tupled, Payment.unapply)
}
