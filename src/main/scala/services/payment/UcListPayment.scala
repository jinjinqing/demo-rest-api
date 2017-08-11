package services.payment

class UcListPayment(finder: PaymentFinder) {

  def list(): List[Payment] = {
    finder.findAll
  }
}
