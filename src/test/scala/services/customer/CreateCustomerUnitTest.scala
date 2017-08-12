package services.customer

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FunSuite }

class CreateCustomerUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val creator = mock[CustomerCreator]
  }

  test("Create new customer successfully") {
    new mocks {
      val service = new CreateCustomer(creator)
      val newCustomer = NewCustomer("test", "test")
      val customerId = UUID.randomUUID()

      (creator.create _).expects(newCustomer).returning(Right(customerId))

      val results = service.create(newCustomer)

      results match {
        case Right(id) => id should be(customerId)
        case _ => fail()
      }
    }
  }

  test("Fail to create customer") {
    new mocks {
      val service = new CreateCustomer(creator)
      val newCustomer = NewCustomer("", "")
      val customerId = UUID.randomUUID()

      (creator.create _).expects(newCustomer).returning(Left(new Exception("fail")))

      val results = service.create(newCustomer)

      results match {
        case Left(f) => f should be("fail")
        case _ => fail()
      }
    }
  }
}
