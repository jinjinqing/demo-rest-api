package demo.business.payment.usecases

import demo.business.payment.boundary.{Payment, PaymentFinder}

class UcListPayment(finder: PaymentFinder) {

  def list(): List[Payment] = {
    finder.findAll
  }
}
