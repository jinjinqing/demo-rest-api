package rest

import java.time.LocalDate
import java.util.UUID

import services.customer.{ CustomerDetails, Customer, NewCustomer }
import services.invoice.{ InvoiceDetails, Invoice, NewInvoice }
import services.payment.{ PaymentDetails, Payment, NewPayment }
import spray.json._

trait RestJsonFormatSupport extends DefaultJsonProtocol {

  implicit object uuidFormat extends RootJsonFormat[UUID] {
    def write(uuid: UUID): JsValue = JsString(uuid.toString)
    def read(json: JsValue): UUID = json match {
      case JsString(v) => UUID.fromString(v)
      case _ => deserializationError("Expected UUID represented as String")
    }
  }

  implicit object localDateFormat extends JsonFormat[LocalDate] {
    def write(obj: LocalDate): JsValue = JsString(obj.toString)
    def read(json: JsValue): LocalDate = json match {
      case JsString(v) => LocalDate.parse(v)
      case _ => deserializationError("Expected LocalDate represented as String")
    }
  }

  implicit val newPaymentFormat = jsonFormat2(NewPayment)
  implicit val paymentFormat = jsonFormat3(Payment)
  implicit val paymentDetailsFormat = jsonFormat3(PaymentDetails)
  implicit val newInvoiceFormat = jsonFormat4(NewInvoice)
  implicit val invoiceFormat = jsonFormat5(Invoice)
  implicit val invoiceDetailsFormat = jsonFormat7(InvoiceDetails)
  implicit val newCustomerFormat = jsonFormat2(NewCustomer)
  implicit val customerFormat = jsonFormat3(Customer)
  implicit val customerDetailsFormat = jsonFormat4(CustomerDetails)
}

object RestJsonFormatSupport extends RestJsonFormatSupport
