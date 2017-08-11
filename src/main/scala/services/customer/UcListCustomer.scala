package services.customer

class UcListCustomer(finder: CustomerFinder) {

  def list(): List[Customer] = {
    finder.findAll
  }

}
