package demo.business.invoice.boundary

import java.time.LocalDate
import java.util.UUID

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
  payments: List[String]
)
