package services.payment

import java.util.UUID

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
