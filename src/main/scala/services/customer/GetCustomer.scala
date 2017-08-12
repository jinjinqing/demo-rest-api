package services.customer

import java.util.UUID
import services.invoice.{ InvoiceDetails, InvoiceFinder }
import services.payment.PaymentFinder

class GetCustomer(customerFinder: CustomerFinder, invoiceFinder: InvoiceFinder, paymentFinder: PaymentFinder) {

  def get(guid: UUID): Either[String, CustomerDetails] = {
    customerFinder.exists(guid) match {
      case true =>
        val customerData = customerFinder.findById(guid).get
        val invoicesDetails = invoiceFinder.findByCustomerId(customerData.id).map(inv => {
          val paymentDetails = paymentFinder.findByInvoiceId(inv.id)
          val alreadyPaid = paymentDetails.map(_.value).sum.toString
          InvoiceDetails(inv.id, inv.customerId, inv.invoiceDate, inv.chargeName, inv.toBePaid.toString, alreadyPaid, paymentDetails)
        })
        Right(CustomerDetails(customerData.id, customerData.firstName, customerData.lastName, invoicesDetails))
      case false => Left("Customer doesn't exist")
    }
  }
}
