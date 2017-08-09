package demo.business.payment.boundary

import java.util.UUID

import scalaz.\/

trait PaymentCreator {
  def create(newPayment: NewPayment): \/[Throwable, UUID]
}
