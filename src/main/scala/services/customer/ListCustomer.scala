package services.customer

class ListCustomer(finder: CustomerFinder) {

  def list(): List[Customer] = {
    finder.findAll
  }

}
