package demo.rest

import java.util.UUID

import demo.business.customer.boundary.{Customer, CustomerDetails, NewCustomer}
import spray.json._

trait RestJsonFormatSupport extends DefaultJsonProtocol {

  implicit object uuidFormat extends RootJsonFormat[UUID] {
    def write(uuid: UUID): JsValue = JsString(uuid.toString)
    def read(json: JsValue): UUID = json match {
      case JsString(v) => UUID.fromString(v)
      case _           => deserializationError("Expected UUID represented as String")
    }
  }

  implicit val newCustomerFormat = jsonFormat2(NewCustomer)
  implicit val customerFormat = jsonFormat3(Customer)
  implicit val customerDetailsFormat = jsonFormat3(CustomerDetails.apply)

}

object RestJsonFormatSupport extends RestJsonFormatSupport {}
