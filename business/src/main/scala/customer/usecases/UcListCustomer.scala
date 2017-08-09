package customer.usecases

import demo.business.customer.boundary.{Customer, CustomerFinder}

class UcListCustomer(finder: CustomerFinder) {

  def list(): List[Customer] = {
    finder.findAll
  }

}
