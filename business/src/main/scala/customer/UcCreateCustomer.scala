package customer

import java.util.UUID

class UcCreateCustomer(creator: CustomerCreator) {

  def create(newCustomer: NewCustomer): Either[String, UUID] = {
    creator.create(newCustomer) match {
      case Right(id)  => Right(id)
      case Left(fail) => Left(fail.getMessage)
    }
  }
}
