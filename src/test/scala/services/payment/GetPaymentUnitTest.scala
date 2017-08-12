package services.payment

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FunSuite }

class GetPaymentUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val creator = mock[PaymentCreator]
    val paymentFinder = mock[PaymentFinder]
  }

  test("Get details of a payment successfully") {
    new mocks {
      val service = new GetPayment(paymentFinder)
      val invoiceId = UUID.randomUUID()
      val paymentId = UUID.randomUUID()

      (paymentFinder.exists _).expects(paymentId).returning(true)
      (paymentFinder.findById _).expects(paymentId).returning(Some(Payment(paymentId, invoiceId, "40.00")))

      val results = service.get(paymentId)

      results match {
        case Right(details) =>
          details.id should be(paymentId)
          details.invoiceId should be(invoiceId)
          details.value should be("40.00")
        case _ => fail()
      }
    }
  }

  test("Fail to get payment details") {
    new mocks {
      val service = new GetPayment(paymentFinder)
      val invoiceId = UUID.randomUUID()

      (paymentFinder.exists _).expects(invoiceId).returning(false)

      val results = service.get(invoiceId)

      results match {
        case Left(f) => f should be("Payment doesn't exist")
        case _ => fail()
      }
    }
  }

}
