package demo.rest

import demo.business.customer.boundary.CustomerFinder
import demo.storage.DemoDatabase
import demo.storage.mysql.customerStorage

trait Storage extends StorageInitializer {
  def customerFinder: CustomerFinder = customerStorage
}

trait StorageInitializer {
  DemoDatabase.init
}

