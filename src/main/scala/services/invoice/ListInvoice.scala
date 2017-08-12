package services.invoice

class ListInvoice(finder: InvoiceFinder) {

  def list(): List[Invoice] = {
    finder.findAll
  }
}
