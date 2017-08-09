package demo.business.customer.boundary

import java.util.UUID
import demo.business.common.Finder

trait CustomerFinder extends Finder[UUID, Customer] {
}
