package rest

import spray.routing._

trait ServiceActor extends HttpServiceActor with Routes {
  override def receive: Receive = runRoute {
    swaggerRoutes
  }
}

object ServiceActor extends ServiceActor

