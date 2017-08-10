package invoice

import java.util.UUID

import demo.business.common.Finder

trait InvoiceFinder extends Finder[UUID, Invoice] {
  def findByCustomerId(guid: UUID): List[Invoice]
}
