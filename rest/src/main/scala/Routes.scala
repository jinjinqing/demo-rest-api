package demo.rest

import akka.actor.ActorRefFactory
import demo.rest.Main.system
import spray.routing._
import spray.routing.directives.BasicDirectives

trait Routes extends Directives with BasicDirectives {

  import com.softwaremill.macwire.MacwireMacros._

  lazy val healthcheckRoute: HealthCheckRoute = wire[HealthCheckRoute]
  lazy val customerRoute: CustomerRoute = wire[CustomerRoute]

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
      swaggerService.routes ~ swaggerUI ~ healthcheckRoute.tree ~ customerRoute.tree
    }
  }
}
