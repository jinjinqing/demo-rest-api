package demo.business.payment.usecases

import java.util.UUID

import demo.business.invoice.boundary.InvoiceFinder
import demo.business.payment.boundary.{NewPayment, PaymentCreator}

class UcCreatePayment(creator: PaymentCreator, invoiceFinder: InvoiceFinder) {

  def create(newPayment: NewPayment): Either[String, UUID] = {
    invoiceFinder.exists(newPayment.invoiceId) match {
      case true =>
        creator.create(newPayment).fold(
          fail => Left(fail.getMessage),
          createdId => Right(createdId)
        )
      case false => Left("InvoiceId doesn't exist")
    }
  }
}
