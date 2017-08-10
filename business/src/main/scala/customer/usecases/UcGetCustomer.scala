package customer.usecases

import java.util.UUID

import demo.business.customer.boundary.{CustomerDetails, CustomerFinder}
import demo.business.invoice.boundary.{InvoiceDetails, InvoiceFinder}
import demo.business.payment.boundary.PaymentFinder

class UcGetCustomer(customerFinder: CustomerFinder, invoiceFinder: InvoiceFinder, paymentFinder: PaymentFinder) {

  def get(guid: UUID): Either[String, CustomerDetails] = {
    customerFinder.exists(guid) match {
      case true =>
        val customerData = customerFinder.findById(guid).get
        val invoicesDetails = invoiceFinder.findByCustomerId(customerData.id).map(inv => {
          val paymentDetails = paymentFinder.findByInvoiceId(inv.id)
          val alreadyPaid = paymentDetails.map(p => BigDecimal(p.value)).sum.toString
          InvoiceDetails(inv.id, inv.customerId, inv.invoiceDate, inv.chargeName, inv.toBePaid, alreadyPaid, paymentDetails)
        })
        Right(CustomerDetails(customerData.id, customerData.firstName, customerData.lastName, invoicesDetails))
      case false => Left("Customer doesn't exist")
    }
  }
}
