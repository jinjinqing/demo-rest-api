package services.payment

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FunSuite }
import services.invoice.InvoiceFinder

class CreatePaymentUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val creator = mock[PaymentCreator]
    val invoiceFinder = mock[InvoiceFinder]
  }

  test("Create new payment successfully") {
    new mocks {
      val service = new CreatePayment(creator, invoiceFinder)
      val invoiceId = UUID.randomUUID()
      val paymentId = UUID.randomUUID()
      val newPayment = NewPayment(invoiceId, "30.00")

      (invoiceFinder.exists _).expects(invoiceId).returning(true)
      (creator.create _).expects(newPayment).returning(Right(paymentId))

      val results = service.create(newPayment)

      results match {
        case Right(id) => id should be(paymentId)
        case _ => fail()
      }
    }
  }

  test("Fail to create payment if invoice doesn't exist") {
    new mocks {
      val service = new CreatePayment(creator, invoiceFinder)
      val invoiceId = UUID.randomUUID()
      val newPayment = NewPayment(invoiceId, "30.00")

      (invoiceFinder.exists _).expects(invoiceId).returning(false)

      val results = service.create(newPayment)

      results match {
        case Left(f) => f should be("InvoiceId doesn't exist")
        case _ => fail()
      }
    }
  }
}
