package demo.rest

import spray.routing._

class RouterActor(applicationRoutes: => Route) extends HttpServiceActor {
  override def receive: Receive = runRoute {
    applicationRoutes
  }
}
