package demo.business.invoice.usecases

import java.util.UUID

import demo.business.invoice.boundary.{InvoiceDetails, InvoiceFinder}

class UcGetInvoice(finder: InvoiceFinder) {

  def get(guid: UUID): Either[String, InvoiceDetails] = {
    finder.exists(guid) match {
      case true =>
        val result = finder.findById(guid).get
        Right(InvoiceDetails(result.id, result.customerId, result.invoiceDate, result.chargeName, result.toBePaid, Nil))
      case false => Left("Invoice doesn't exist")
    }
  }
}
