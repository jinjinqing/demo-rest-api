package demo.business.payment.boundary

import java.util.UUID
import demo.business.common.Finder

trait PaymentFinder extends Finder[UUID, Payment] {
}
