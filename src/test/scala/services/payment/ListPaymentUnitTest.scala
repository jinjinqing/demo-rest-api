package services.payment

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FunSuite }

class ListPaymentUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val finder = mock[PaymentFinder]
  }

  test("List all available payments") {
    new mocks {
      val service = new ListPayment(finder)
      val id1 = UUID.randomUUID()
      val id2 = UUID.randomUUID()
      val invoiceId = UUID.randomUUID()

      (finder.findAll _).expects().returning(List(Payment(id1, invoiceId, "20"), Payment(id2, invoiceId, "40")))

      val result = service.list()

      result.size should be(2)
      result.map(_.id) should be(List(id1, id2))
      result.map(_.invoiceId) should be(List(invoiceId, invoiceId))
      result.map(_.value) should be(List("20", "40"))
    }
  }
}

