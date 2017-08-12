package services.invoice

import java.time.LocalDate
import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FunSuite }

class ListInvoiceUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val finder = mock[InvoiceFinder]
  }

  test("List all available invoices") {
    new mocks {
      val service = new ListInvoice(finder)
      val id1 = UUID.randomUUID()
      val id2 = UUID.randomUUID()
      val customerId = UUID.randomUUID()
      val date = LocalDate.parse("2005-11-12")

      (finder.findAll _).expects().returning(List(Invoice(id1, customerId, date, "tv", "20"), Invoice(id2, customerId, date, "internet", "40")))

      val result = service.list()

      result.size should be(2)
      result.map(_.id) should be(List(id1, id2))
      result.map(_.customerId) should be(List(customerId, customerId))
      result.map(_.chargeName) should be(List("tv", "internet"))
      result.map(_.toBePaid) should be(List("20", "40"))
    }
  }
}
