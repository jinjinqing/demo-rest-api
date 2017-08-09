package demo.business.customer.usecases

import java.util.UUID

import demo.business.customer.boundary.{CustomerCreator, NewCustomer}

class UcCreateCustomer(creator: CustomerCreator) {

  def create(newCustomer: NewCustomer): Either[String, UUID] = {
    creator.create(newCustomer).fold(
      fail => Left(fail.getMessage),
      createdId => Right(createdId)
    )
  }
}
