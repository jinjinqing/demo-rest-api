package rest

import akka.actor.ActorRefFactory
import spray.routing._
import spray.routing.directives.BasicDirectives

/*
class RouterActor(applicationRoutes: => Route) extends HttpServiceActor {
  override def receive: Receive = runRoute {
    applicationRoutes
  }
}
*/
trait ServiceActor extends HttpServiceActor with ServicesInitializer with Directives with BasicDirectives {
  lazy val swaggerRoutes = {

    def swaggerUI = get {
      pathPrefix("") {
        pathEndOrSingleSlash {
          getFromResource("swagger-ui/index.html")
        }
      } ~
        getFromResourceDirectory("swagger-ui")
    }

    val swaggerService = new SwaggerService {
      override def actorRefFactory: ActorRefFactory = context
    }

    swaggerUI ~ swaggerService.routes
  }

  def receive = runRoute(
    pathPrefix("demo") {
      swaggerRoutes ~ customerRoute.tree ~ invoiceRoute.tree ~ paymentRoute.tree
    }
  )
}

object ServiceActor extends ServiceActor

