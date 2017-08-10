package invoice

import java.util.UUID

trait InvoiceCreator {
  def create(newInvoice: NewInvoice): Either[Throwable, UUID]
}
