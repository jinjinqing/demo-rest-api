package demo.business.customer.boundary

import java.util.UUID

import scalaz.\/

trait CustomerCreator {
  def create(newCustomer: NewCustomer): \/[Throwable, UUID]
}

