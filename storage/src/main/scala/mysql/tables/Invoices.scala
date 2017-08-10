package demo.storage.mysql.tables

import java.time.LocalDate
import java.util.UUID
import demo.storage.mysql.customerStorage
import invoice.Invoice
import slick.jdbc.MySQLProfile.api._

class Invoices(tag: Tag) extends Table[Invoice](tag, "INVOICES") {
  def id = column[UUID]("ID", O.PrimaryKey)
  def customerId = column[UUID]("CUSTOMER_ID")
  def invoiceDate = column[LocalDate]("INVOICE_DATE", O.SqlType("DATETIME"))
  def chargeName = column[String]("CHARGE_NAME")
  def toBePaid = column[String]("TO_BE_PAID")

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