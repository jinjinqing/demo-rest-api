package rest

import akka.actor.ActorRefFactory
import rest.Main.system
import spray.routing._
import spray.routing.directives.BasicDirectives

trait Routes extends ServicesInitializer with Directives with BasicDirectives {

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
      val actorRefFactory: ActorRefFactory = system
    }

    pathPrefix("demo") {
      swaggerService.routes ~
        swaggerUI ~
        customerRoute.tree ~
        invoiceRoute.tree ~
        paymentRoute.tree
    }
  }
}