package database.tables

import java.util.UUID
import services.customer.Customer
import slick.jdbc.MySQLProfile.api._

class Customers(tag: Tag) extends Table[Customer](tag, "CUSTOMERS") {
  def id = column[UUID]("ID", O.PrimaryKey)
  def firstName = column[String]("FIRST_NAME")
  def lastName = column[String]("LAST_NAME")

  override def * = (id, firstName, lastName) <> (Customer.tupled, Customer.unapply)
}
