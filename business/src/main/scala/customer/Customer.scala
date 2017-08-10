package customer

import java.util.UUID

import invoice.InvoiceDetails

case class Customer(
  id: UUID,
  firstName: String,
  lastName: String
)

case class CustomerDetails(
  id: UUID,
  firstName: String,
  lastName: String,
  invoices: List[InvoiceDetails]
)

case class NewCustomer(
  firstName: String,
  lastName: String
)
