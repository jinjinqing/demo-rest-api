package demo.business.customer.boundary

import java.util.UUID

case class Customer(
  id: UUID,
  firstName: String,
  lastName: String
) extends Serializable

case class CustomerDetails(
  id: UUID,
  firstName: String,
  lastName: String
)

case class NewCustomer(
  firstName: String,
  lastName: String
)
