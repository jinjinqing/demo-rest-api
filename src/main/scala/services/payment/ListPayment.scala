package services.payment

class ListPayment(finder: PaymentFinder) {

  def list(): List[Payment] = {
    finder.findAll
  }
}
