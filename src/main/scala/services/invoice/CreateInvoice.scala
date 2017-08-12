package services.invoice

import java.util.UUID

import services.customer.CustomerFinder

class CreateInvoice(creator: InvoiceCreator, customerFinder: CustomerFinder) {

  def create(newInvoice: NewInvoice): Either[String, UUID] = {
    customerFinder.exists(newInvoice.customerId) match {
      case true =>
        creator.create(newInvoice) match {
          case Left(fail) => Left(fail.getMessage)
          case Right(createdId) => Right(createdId)
        }
      case false => Left("CustomerId doesn't exist")
    }
  }
}
