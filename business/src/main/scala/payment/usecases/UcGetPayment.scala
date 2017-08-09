package demo.business.payment.usecases

import java.util.UUID

import demo.business.payment.boundary.{PaymentDetails, PaymentFinder}

class UcGetPayment(finder: PaymentFinder) {

  def get(guid: UUID): Either[String, PaymentDetails] = {
    finder.exists(guid) match {
      case true =>
        val result = finder.findById(guid).get
        Right(PaymentDetails(result.id, result.invoiceId, result.value))
      case false => Left("Payment doesn't exist")
    }
  }

}
