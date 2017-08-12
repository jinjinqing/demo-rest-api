package services.customer

import java.time.LocalDate
import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FunSuite }
import services.invoice.{ Invoice, InvoiceFinder }
import services.payment.{ Payment, PaymentFinder }

class GetCustomerUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val customerFinder = mock[CustomerFinder]
    val invoiceFinder = mock[InvoiceFinder]
    val paymentFinder = mock[PaymentFinder]
  }

  test("Get details of a customer successfully") {
    new mocks {
      val service = new GetCustomer(customerFinder, invoiceFinder, paymentFinder)
      val customerId = UUID.randomUUID()
      val invoiceId = UUID.randomUUID()
      val paymentId = UUID.randomUUID()
      val date = LocalDate.parse("2005-11-12")

      (paymentFinder.findByInvoiceId _).expects(invoiceId).returning(List(Payment(paymentId, invoiceId, BigDecimal("30.00"))))
      (invoiceFinder.findByCustomerId _).expects(customerId).returning(List(Invoice(invoiceId, customerId, date, "Mobile", BigDecimal("40.00"))))
      (customerFinder.findById _).expects(customerId).returning(Some(Customer(customerId, "aa", "bb")))
      (customerFinder.exists _).expects(customerId).returning(true)

      val results = service.get(customerId)

      results match {
        case Right(details) =>
          details.id should be(customerId)
          details.firstName should be("aa")
          details.lastName should be("bb")
          details.invoices.size should be(1)
          details.invoices.head.id should be(invoiceId)
          details.invoices.head.chargeName should be("Mobile")
          details.invoices.head.invoiceDate should be(date)
          details.invoices.head.alreadyPaid should be("30.00")
          details.invoices.head.toBePaid should be("40.00")
          details.invoices.head.payments.head.id should be(paymentId)
          details.invoices.head.payments.head.value should be("30.00")
        case _ => fail()
      }
    }
  }

  test("Fail to get customer details") {
    new mocks {
      val service = new GetCustomer(customerFinder, invoiceFinder, paymentFinder)
      val customerId = UUID.randomUUID()

      (customerFinder.exists _).expects(customerId).returning(false)

      val results = service.get(customerId)

      results match {
        case Left(f) => f should be("Customer doesn't exist")
        case _ => fail()
      }
    }
  }
}
