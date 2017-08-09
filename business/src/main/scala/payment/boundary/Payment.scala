package demo.business.payment.boundary

import java.util.UUID

case class Payment(
  id: UUID,
  invoiceId: UUID,
  value: String
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