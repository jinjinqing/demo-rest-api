package demo.business.invoice.usecases

import java.util.UUID

import demo.business.customer.boundary.CustomerFinder
import demo.business.invoice.boundary.{NewInvoice, InvoiceCreator}

class UcCreateInvoice(creator: InvoiceCreator, customerFinder: CustomerFinder) {

  def create(newInvoice: NewInvoice): Either[String, UUID] = {
    customerFinder.exists(newInvoice.customerId) match {
      case true =>
        creator.create(newInvoice).fold(
          fail => Left(fail.getMessage),
          createdId => Right(createdId)
        )
      case false => Left("CustomerId doesn't exist")
    }
  }
}
