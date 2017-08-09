package demo.rest

import customer.usecases.{UcListCustomer, UcGetCustomer}
import demo.business.customer.boundary.{CustomerCreator, CustomerFinder}
import demo.business.customer.usecases.UcCreateCustomer
import demo.storage.DemoDatabase
import demo.storage.mysql.customerStorage

trait Storage extends StorageInitializer {
  def customerFinder: CustomerFinder = customerStorage
  def customerCreator: CustomerCreator = customerStorage
}

trait StorageInitializer {
  DemoDatabase.init
}

trait businessInitializer extends Storage with StorageInitializer {
  lazy val healthcheckRoute: HealthCheckRoute = new HealthCheckRoute
  lazy val customerRoute: CustomerRoute = new CustomerRoute(ucCreateCustomer, ucGetCustomer, ucListCustomer)

  lazy val ucCreateCustomer: UcCreateCustomer = new UcCreateCustomer(customerCreator)
  lazy val ucGetCustomer: UcGetCustomer = new UcGetCustomer(customerFinder)
  lazy val ucListCustomer: UcListCustomer = new UcListCustomer(customerFinder)
}
