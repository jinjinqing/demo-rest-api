package services.invoice

import java.time.LocalDate
import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FunSuite }
import services.customer.CustomerFinder

class CreateInvoiceUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val creator = mock[InvoiceCreator]
    val customerFinder = mock[CustomerFinder]
  }

  test("Create new invoice successfully") {
    new mocks {
      val service = new CreateInvoice(creator, customerFinder)
      val invoiceId = UUID.randomUUID()
      val customerId = UUID.randomUUID()
      val date = LocalDate.parse("2005-11-12")
      val newInvoice = NewInvoice(customerId, date, "Mobile", "30.00")

      (customerFinder.exists _).expects(customerId).returning(true)
      (creator.create _).expects(newInvoice).returning(Right(invoiceId))

      val results = service.create(newInvoice)

      results match {
        case Right(id) => id should be(invoiceId)
        case _ => fail()
      }
    }
  }

  test("Fail to create invoice if customer doesn't exist") {
    new mocks {
      val service = new CreateInvoice(creator, customerFinder)
      val invoiceId = UUID.randomUUID()
      val customerId = UUID.randomUUID()
      val date = LocalDate.parse("2005-11-12")
      val newInvoice = NewInvoice(customerId, date, "Mobile", "30.00")

      (customerFinder.exists _).expects(customerId).returning(false)

      val results = service.create(newInvoice)

      results match {
        case Left(f) => f should be("CustomerId doesn't exist")
        case _ => fail()
      }
    }
  }
}