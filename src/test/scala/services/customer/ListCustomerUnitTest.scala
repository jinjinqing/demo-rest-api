package services.customer

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FunSuite }

class ListCustomerUnitTest extends FunSuite with MockFactory with Matchers {

  trait mocks {
    val finder = mock[CustomerFinder]
  }

  test("List all available customers") {
    new mocks {
      val service = new ListCustomer(finder)
      val id1 = UUID.randomUUID()
      val id2 = UUID.randomUUID()

      (finder.findAll _).expects().returning(List(Customer(id1, "aa", "bb"), Customer(id2, "cc", "dd")))

      val result = service.list()

      result.size should be(2)
      result.map(_.id) should be(List(id1, id2))
      result.map(_.firstName) should be(List("aa", "cc"))
      result.map(_.lastName) should be(List("bb", "dd"))
    }
  }
}
