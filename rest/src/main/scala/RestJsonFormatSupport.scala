package demo.rest

import java.time.LocalDate
import java.util.UUID

import demo.business.customer.boundary.{Customer, CustomerDetails, NewCustomer}
import demo.business.invoice.boundary.{InvoiceDetails, Invoice, NewInvoice}
import demo.business.payment.boundary.{PaymentDetails, Payment, NewPayment}
import spray.json._

trait RestJsonFormatSupport extends DefaultJsonProtocol {

  implicit object uuidFormat extends RootJsonFormat[UUID] {
    def write(uuid: UUID): JsValue = JsString(uuid.toString)
    def read(json: JsValue): UUID = json match {
      case JsString(v) => UUID.fromString(v)
      case _           => deserializationError("Expected UUID represented as String")
    }
  }

  implicit object localDateFormat extends JsonFormat[LocalDate] {
    def write(obj: LocalDate): JsValue = JsString(obj.toString)
    def read(json: JsValue): LocalDate = json match {
      case JsString(v) => LocalDate.parse(v)
      case _           => deserializationError("Expected LocalDate represented as String")
    }
  }

  implicit val newCustomerFormat = jsonFormat2(NewCustomer)
  implicit val customerFormat = jsonFormat3(Customer)
  implicit val customerDetailsFormat = jsonFormat3(CustomerDetails)
  implicit val newInvoiceFormat = jsonFormat4(NewInvoice)
  implicit val invoiceFormat = jsonFormat5(Invoice)
  implicit val invoiceDetailsFormat = jsonFormat6(InvoiceDetails)
  implicit val newPaymentFormat = jsonFormat2(NewPayment)
  implicit val paymentFormat = jsonFormat3(Payment)
  implicit val paymentDetailsFormat = jsonFormat3(PaymentDetails)
}

object RestJsonFormatSupport extends RestJsonFormatSupport {}
