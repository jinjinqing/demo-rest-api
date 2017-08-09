package invoice.usecases

import demo.business.invoice.boundary.{Invoice, InvoiceFinder}

class UcListInvoice(finder: InvoiceFinder) {

  def list(): List[Invoice] = {
    finder.findAll
  }
}
