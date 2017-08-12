package database.tables

import java.time.LocalDate
import java.util.UUID
import database.customerStorage
import services.invoice.Invoice
import slick.jdbc.MySQLProfile.api._

class Invoices(tag: Tag) extends Table[Invoice](tag, "INVOICES") {
  def id = column[UUID]("ID", O.PrimaryKey)
  def customerId = column[UUID]("CUSTOMER_ID")
  def invoiceDate = column[LocalDate]("INVOICE_DATE", O.SqlType("DATETIME"))
  def chargeName = column[String]("CHARGE_NAME")
  def toBePaid = column[BigDecimal]("TO_BE_PAID", O.SqlType("decimal(10, 2)"))

  def customer = foreignKey(
    "INVOICES_CUSTOMER_ID_FK",
    customerId, customerStorage
  )(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

  implicit val LocalDate2SqlDate = {
    MappedColumnType.base[java.time.LocalDate, java.sql.Date](
      localDate => java.sql.Date.valueOf(localDate),
      sqlDate => sqlDate.toLocalDate
    )
  }

  override def * = (id, customerId, invoiceDate, chargeName, toBePaid) <> (Invoice.tupled, Invoice.unapply)
}