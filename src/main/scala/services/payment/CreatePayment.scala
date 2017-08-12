package services.payment

import java.util.UUID

import services.invoice.InvoiceFinder

class CreatePayment(creator: PaymentCreator, invoiceFinder: InvoiceFinder) {

  def create(newPayment: NewPayment): Either[String, UUID] = {
    invoiceFinder.exists(newPayment.invoiceId) match {
      case true =>
        creator.create(newPayment) match {
          case Left(fail) => Left(fail.getMessage)
          case Right(createdId) => Right(createdId)
        }
      case false => Left("InvoiceId doesn't exist")
    }
  }
}
