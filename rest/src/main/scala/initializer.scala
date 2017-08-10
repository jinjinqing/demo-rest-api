package demo.rest

import customer.usecases.{UcListCustomer, UcGetCustomer}
import demo.business.customer.boundary.{CustomerCreator, CustomerFinder}
import demo.business.customer.usecases.UcCreateCustomer
import demo.business.invoice.boundary.{InvoiceFinder, InvoiceCreator}
import demo.business.invoice.usecases.{UcListInvoice, UcGetInvoice, UcCreateInvoice}
import demo.business.payment.boundary.{PaymentCreator, PaymentFinder}
import demo.business.payment.usecases.{UcCreatePayment, UcGetPayment, UcListPayment}
import demo.storage.DemoDatabase
import demo.storage.mysql.{paymentStorage, invoiceStorage, customerStorage}

trait Storage {
  def customerFinder: CustomerFinder = customerStorage
  def customerCreator: CustomerCreator = customerStorage
  def invoiceFinder: InvoiceFinder = invoiceStorage
  def invoiceCreator: InvoiceCreator = invoiceStorage
  def paymentFinder: PaymentFinder = paymentStorage
  def paymentCreator: PaymentCreator = paymentStorage
}

trait StorageInitializer {
  DemoDatabase.init
}

trait businessInitializer extends Storage with StorageInitializer {
  lazy val healthcheckRoute: HealthCheckRoute = new HealthCheckRoute

  lazy val customerRoute: CustomerRoute = new CustomerRoute(ucCreateCustomer, ucGetCustomer, ucListCustomer)
  lazy val ucCreateCustomer: UcCreateCustomer = new UcCreateCustomer(customerCreator)
  lazy val ucGetCustomer: UcGetCustomer = new UcGetCustomer(customerFinder, invoiceFinder, paymentFinder)
  lazy val ucListCustomer: UcListCustomer = new UcListCustomer(customerFinder)

  lazy val invoiceRoute: InvoiceRoute = new InvoiceRoute(ucCreateInvoice, ucGetInvoice, ucListInvoice)
  lazy val ucCreateInvoice: UcCreateInvoice = new UcCreateInvoice(invoiceCreator, customerFinder)
  lazy val ucGetInvoice: UcGetInvoice = new UcGetInvoice(invoiceFinder, paymentFinder)
  lazy val ucListInvoice: UcListInvoice = new UcListInvoice(invoiceFinder)

  lazy val paymentRoute: PaymentRoute = new PaymentRoute(ucCreatePayment, ucGetPayment, ucListPayment)
  lazy val ucCreatePayment: UcCreatePayment = new UcCreatePayment(paymentCreator, invoiceFinder)
  lazy val ucGetPayment: UcGetPayment = new UcGetPayment(paymentFinder)
  lazy val ucListPayment: UcListPayment = new UcListPayment(paymentFinder)
}
