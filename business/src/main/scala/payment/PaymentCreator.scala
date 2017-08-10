package payment

import java.util.UUID

trait PaymentCreator {
  def create(newPayment: NewPayment): Either[Throwable, UUID]
}
