package services.invoice

import java.util.UUID

import services.payment.PaymentFinder

class GetInvoice(finder: InvoiceFinder, paymentFinder: PaymentFinder) {

  def get(guid: UUID): Either[String, InvoiceDetails] = {
    finder.exists(guid) match {
      case true =>
        val result = finder.findById(guid).get
        val payments = paymentFinder.findByInvoiceId(guid)
        val alreadyPaid = payments.map(_.value).sum.toString
        Right(InvoiceDetails(result.id, result.customerId, result.invoiceDate, result.chargeName, result.toBePaid.toString, alreadyPaid, payments))
      case false => Left("Invoice doesn't exist")
    }
  }
}
