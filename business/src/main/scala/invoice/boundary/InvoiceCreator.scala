package demo.business.invoice.boundary

import java.util.UUID

import scalaz.\/

trait InvoiceCreator {
  def create(newInvoice: NewInvoice): \/[Throwable, UUID]
}
