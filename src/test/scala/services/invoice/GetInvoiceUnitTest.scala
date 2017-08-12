package services.invoice

import java.time.LocalDate
import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FunSuite }
import services.payment.{ Payment, PaymentFinder }

class GetInvoiceUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val invoiceFinder = mock[InvoiceFinder]
    val paymentFinder = mock[PaymentFinder]
  }

  test("Get details of a invoice successfully") {
    new mocks {
      val service = new GetInvoice(invoiceFinder, paymentFinder)
      val customerId = UUID.randomUUID()
      val invoiceId = UUID.randomUUID()
      val paymentId = UUID.randomUUID()
      val date = LocalDate.parse("2005-11-12")

      (invoiceFinder.exists _).expects(invoiceId).returning(true)
      (paymentFinder.findByInvoiceId _).expects(invoiceId).returning(List(Payment(paymentId, invoiceId, 30.00)))
      (invoiceFinder.findById _).expects(invoiceId).returning(Some(Invoice(invoiceId, customerId, date, "Mobile", 40.00)))

      val results = service.get(invoiceId)

      results match {
        case Right(details) =>
          details.id should be(invoiceId)
          details.customerId should be(customerId)
          details.chargeName should be("Mobile")
          details.invoiceDate should be(date)
          details.alreadyPaid should be("30.00")
          details.toBePaid should be("40.00")
          details.payments.head.id should be(paymentId)
          details.payments.head.value should be("30.00")
        case _ => fail()
      }
    }
  }

  test("Fail to get invoice details") {
    new mocks {
      val service = new GetInvoice(invoiceFinder, paymentFinder)
      val invoiceId = UUID.randomUUID()

      (invoiceFinder.exists _).expects(invoiceId).returning(false)

      val results = service.get(invoiceId)

      results match {
        case Left(f) => f should be("Invoice doesn't exist")
        case _ => fail()
      }
    }
  }
}
