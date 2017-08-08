package demo.rest

import javax.ws.rs.Path

import io.swagger.annotations.{Api, ApiOperation}
import spray.http.MediaTypes._
import spray.routing.Directives

@Path("/health")
@Api(value = "/health")
class HealthCheckRoute extends Directives {

  @ApiOperation(httpMethod = "GET", produces = "application/json", value = "Returns 'I'm alive' message if system is up and running")
  def tree = path("health") {
    get {
      respondWithMediaType(`application/json`) {
        complete {
          """{"status":"I'm alive"}"""
        }
      }
    }
  }
}

