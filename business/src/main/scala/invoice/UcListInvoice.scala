package invoice

class UcListInvoice(finder: InvoiceFinder) {

  def list(): List[Invoice] = {
    finder.findAll
  }
}