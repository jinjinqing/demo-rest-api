package payment

import java.util.UUID

import demo.business.common.Finder

trait PaymentFinder extends Finder[UUID, Payment] {
  def findByInvoiceId(id: UUID): List[Payment]
}
