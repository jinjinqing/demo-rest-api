package services.customer

import java.util.UUID

import services.common.Finder
import services.invoice.InvoiceDetails

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

trait CustomerCreator {
  def create(newCustomer: NewCustomer): Either[Throwable, UUID]
}

trait CustomerFinder extends Finder[UUID, Customer] {
}