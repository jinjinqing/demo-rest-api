package services.invoice

import java.time.LocalDate
import java.util.UUID

import services.common.Finder
import services.payment.Payment

case class Invoice(
  id: UUID,
  customerId: UUID,
  invoiceDate: LocalDate,
  chargeName: String,
  toBePaid: BigDecimal
)

case class NewInvoice(
  customerId: UUID,
  invoiceDate: LocalDate,
  chargeName: String,
  toBePaid: String
)

case class InvoiceDetails(
  id: UUID,
  customerId: UUID,
  invoiceDate: LocalDate,
  chargeName: String,
  toBePaid: String,
  alreadyPaid: String,
  payments: List[Payment]
)

trait InvoiceCreator {
  def create(newInvoice: NewInvoice): Either[Throwable, UUID]
}

trait InvoiceFinder extends Finder[UUID, Invoice] {
  def findByCustomerId(guid: UUID): List[Invoice]
}
