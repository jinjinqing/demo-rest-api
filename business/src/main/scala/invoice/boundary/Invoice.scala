package demo.business.invoice.boundary

import java.time.LocalDate
import java.util.UUID

import demo.business.payment.boundary.Payment

case class Invoice(
  id: UUID,
  customerId: UUID,
  invoiceDate: LocalDate,
  chargeName: String,
  toBePaid: String
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
