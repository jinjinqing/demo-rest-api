package customer

import java.util.UUID

trait CustomerCreator {
  def create(newCustomer: NewCustomer): Either[Throwable, UUID]
}

