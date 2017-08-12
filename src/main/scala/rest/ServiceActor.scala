package rest

import spray.routing._

/*
class RouterActor(applicationRoutes: => Route) extends HttpServiceActor {
  override def receive: Receive = runRoute {
    applicationRoutes
  }
}
*/
class ServiceActor extends HttpServiceActor with Routes {
  override def receive: Receive = runRoute {
    swaggerRoutes
  }
}

//object ServiceActor extends ServiceActor

