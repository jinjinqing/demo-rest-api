package demo.business.invoice.boundary

import java.util.UUID

import demo.business.common.Finder

trait InvoiceFinder extends Finder[UUID, Invoice] {
}