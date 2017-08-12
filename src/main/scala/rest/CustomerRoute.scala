package rest

import java.util.UUID
import javax.ws.rs.Path

import services.customer._
import io.swagger.annotations._
import spray.http._
import spray.httpx.marshalling.ToResponseMarshaller
import spray.routing._
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller
import spray.json._

import scala.annotation.meta.field

@Path("/customers")
@Api(value = "/customers")
class CustomerRoute(
    ucCreateCustomer: CreateCustomer,
    ucGetCustomer: GetCustomer,
    ucListCustomer: ListCustomer
) extends Directives with RestJsonFormatSupport {

  val customerPath = "customers"

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
        post {
          pathEnd {
            createCustomer()
          }
        }
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Returns a list of available Customers")
  def list = {
    implicit val getCustomerMarshaller = ToResponseMarshaller.of[List[Customer]](
      ContentTypes.`application/json`, ContentTypes.`text/plain(UTF-8)`, ContentTypes.`text/plain`
    ) {
      (result, contentType, context) =>
        val ctx = context.withContentTypeOverriding(contentType)
        ctx.marshalTo(HttpResponse(StatusCodes.OK, result.toJson.prettyPrint))
    }
    complete(ucListCustomer.list)
  }

  @ApiOperation(value = "Creates a customer", httpMethod = "POST", consumes = "application/json")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "Customer object that needs to be created",
      dataType = "rest.NewCustomerSwagger", required = true, paramType = "body")
  ))
  def createCustomer() = {
    entity(as[NewCustomer]) { customer =>
      ucCreateCustomer.create(customer) match {
        case Left(error) => complete(StatusCodes.InternalServerError)
        case Right(id) => complete(StatusCodes.Created)
      }
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Returns detailed information about customer")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "customerId", required = true, dataType = "string", paramType = "path",
      value = "ID of the Customer that needs to be fetched")
  ))
  @Path("/{customerId}")
  def getCustomer(@ApiParam(hidden = true) guid: UUID) = {

    implicit val getCustomerMarshaller = ToResponseMarshaller.of[CustomerDetails](
      ContentTypes.`application/json`, ContentTypes.`text/plain(UTF-8)`, ContentTypes.`text/plain`
    ) {
      (result, contentType, context) =>
        val ctx = context.withContentTypeOverriding(contentType)
        ctx.marshalTo(HttpResponse(StatusCodes.OK, result.toJson.prettyPrint))
    }

    ucGetCustomer.get(guid) match {
      case Left(error) => complete(StatusCodes.NotFound)
      case Right(detail) => complete(detail)
    }
  }
}

case class NewCustomerSwagger(
  @(ApiModelProperty @field)(required = true) firstName: String,
  @(ApiModelProperty @field)(required = true) lastName: String
)
