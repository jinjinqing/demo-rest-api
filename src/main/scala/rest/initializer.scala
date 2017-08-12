package rest

import database.{ paymentStorage, invoiceStorage, customerStorage, DatabaseHelper }
import services.invoice._
import services.payment._
import services.customer._

trait DataBaseInitializer {
  DatabaseHelper.dataBaseInit
}

trait ServicesInitializer {
  def customerFinder: CustomerFinder = customerStorage
  def customerCreator: CustomerCreator = customerStorage
  def invoiceFinder: InvoiceFinder = invoiceStorage
  def invoiceCreator: InvoiceCreator = invoiceStorage
  def paymentFinder: PaymentFinder = paymentStorage
  def paymentCreator: PaymentCreator = paymentStorage

  lazy val customerRoute: CustomerRoute = new CustomerRoute(createCustomer, getCustomer, listCustomer)
  lazy val createCustomer: CreateCustomer = new CreateCustomer(customerCreator)
  lazy val getCustomer: GetCustomer = new GetCustomer(customerFinder, invoiceFinder, paymentFinder)
  lazy val listCustomer: ListCustomer = new ListCustomer(customerFinder)

  lazy val invoiceRoute: InvoiceRoute = new InvoiceRoute(createInvoice, getInvoice, listInvoice)
  lazy val createInvoice: CreateInvoice = new CreateInvoice(invoiceCreator, customerFinder)
  lazy val getInvoice: GetInvoice = new GetInvoice(invoiceFinder, paymentFinder)
  lazy val listInvoice: ListInvoice = new ListInvoice(invoiceFinder)

  lazy val paymentRoute: PaymentRoute = new PaymentRoute(createPayment, getPayment, listPayment)
  lazy val createPayment: CreatePayment = new CreatePayment(paymentCreator, invoiceFinder)
  lazy val getPayment: GetPayment = new GetPayment(paymentFinder)
  lazy val listPayment: ListPayment = new ListPayment(paymentFinder)
}
