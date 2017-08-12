package customer

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, FunSuite}

class CreateCustomerUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val creator = mock[CustomerCreator]
  }

  test("Create new customer successfully") {
    new mocks {
      val uc = new UcCreateCustomer(creator)
      val newCustomer = NewCustomer("test", "")
      val customerId = UUID.randomUUID()

      (creator.create _).expects(newCustomer).returning(Right(customerId))

      val results = uc.create(newCustomer)

      results match {
        case Right(id) => id should be(customerId)
        case _         => fail()
      }
    }
  }


}
