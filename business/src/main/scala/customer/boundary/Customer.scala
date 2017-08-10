package demo.business.customer.boundary

import java.util.UUID

import demo.business.invoice.boundary.InvoiceDetails

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
