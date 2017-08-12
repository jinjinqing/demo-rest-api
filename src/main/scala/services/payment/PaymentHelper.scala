package services.payment

import java.util.UUID

import services.common.Finder

case class Payment(
  id: UUID,
  invoiceId: UUID,
  value: BigDecimal
)

case class NewPayment(
  invoiceId: UUID,
  value: String
)

case class PaymentDetails(
  id: UUID,
  invoiceId: UUID,
  value: String
)

trait PaymentCreator {
  def create(newPayment: NewPayment): Either[Throwable, UUID]
}

trait PaymentFinder extends Finder[UUID, Payment] {
  def findByInvoiceId(id: UUID): List[Payment]
}
