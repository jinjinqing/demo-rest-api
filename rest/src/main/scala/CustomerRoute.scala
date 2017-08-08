package demo.rest

import java.util.UUID
import javax.ws.rs.Path

import io.swagger.annotations._
import spray.http.StatusCodes
import spray.routing._

import scala.annotation.meta.field

@Path("/customer")
@Api(value = "/customer")
class CustomerRoute extends Directives {

  val customerPath = "customer"

  def tree: Route = {
    pathPrefix(customerPath) {
      get {
        pathEndOrSingleSlash {
          list
        } ~
          pathPrefix(JavaUUID) { guid =>
            pathEnd {
              getCustomer(guid)
            }
          }
      } ~
        put {
          pathPrefix(JavaUUID) { guid =>
            pathEnd {
              createCustomer(guid)
            }
          }
        }
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Returns a list of available Customers")
  def list = {
    complete(StatusCodes.Created)
  }

  @ApiOperation(value = "Creates a customer", httpMethod = "PUT", consumes = "application/json")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "customerId", required = true, dataType = "string", paramType = "path",
      value = "Id of the Customer that is going to be created"),
    new ApiImplicitParam(name = "body", value = "Customer object that needs to be created",
      dataType = "com.demo.external.rest.NewCustomer", required = true, paramType = "body")
  ))
  @Path("{customerId}")
  def createCustomer(@ApiParam(hidden = true) guid: UUID) = {
     complete(StatusCodes.Created)
  }

  @ApiOperation(httpMethod = "GET", value = "Returns detailed information about customer")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "customerId", required = true, dataType = "string", paramType = "path",
      value = "ID of the Customer that needs to be fetched")
  ))
  @Path("/{customerId}")
  def getCustomer(@ApiParam(hidden = true) guid: UUID): StandardRoute = {
    complete(StatusCodes.Created)
  }

}

case class NewCustomer(
  @(ApiModelProperty @field)(required = true) firstName: String,
  @(ApiModelProperty @field)(required = true) lastName: String
)

