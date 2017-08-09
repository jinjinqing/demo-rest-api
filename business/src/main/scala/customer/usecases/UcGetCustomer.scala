package customer.usecases

import java.util.UUID

import demo.business.customer.boundary.{CustomerDetails, CustomerFinder}

class UcGetCustomer(finder: CustomerFinder) {

  def get(guid: UUID): Either[String, CustomerDetails] = {
    finder.exists(guid) match {
      case true =>
        val result = finder.findById(guid).get
        Right(CustomerDetails(result.Id, result.firstName, result.lastName))
      case false => Left("Customer doesn't exist")
    }
  }
}
