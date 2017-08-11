package rest

import java.util.UUID
import javax.ws.rs.Path

import io.swagger.annotations._
import services.payment._
import spray.http._
import spray.httpx.marshalling.ToResponseMarshaller
import spray.routing._
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller
import spray.json._

import scala.annotation.meta.field

@Path("/payment")
@Api(value = "/payment")
class PaymentRoute(
    ucCreatePayment: UcCreatePayment,
    ucGetPayment: UcGetPayment,
    ucListPayment: UcListPayment
) extends Directives with RestJsonFormatSupport {

  val paymentPath = "payment"

  def tree: Route = {
    pathPrefix(paymentPath) {
      get {
        pathEndOrSingleSlash {
          list
        } ~
          pathPrefix(JavaUUID) { guid =>
            pathEnd {
              getPayment(guid)
            }
          }
      } ~
        post {
          pathEnd {
            createPayment()
          }
        }
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Returns a list of available Payments")
  def list = {
    implicit val listPaymentMarshaller = ToResponseMarshaller.of[List[Payment]](
      ContentTypes.`application/json`, ContentTypes.`text/plain(UTF-8)`, ContentTypes.`text/plain`
    ) {
      (result, contentType, context) =>
        val ctx = context.withContentTypeOverriding(contentType)
        ctx.marshalTo(HttpResponse(StatusCodes.OK, result.toJson.prettyPrint))
    }
    complete(ucListPayment.list())
  }

  @ApiOperation(value = "Creates a payment", httpMethod = "POST", consumes = "application/json")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "Payment object that needs to be created",
      dataType = "demo.rest.NewPaymentSwagger", required = true, paramType = "body")
  ))
  def createPayment() = {
    entity(as[NewPayment]) { payment =>
      ucCreatePayment.create(payment) match {
        case Left(error) => complete(StatusCodes.UnprocessableEntity)
        case Right(id) => complete(StatusCodes.Created)
      }
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Returns detailed information about payment")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "paymentId", required = true, dataType = "string", paramType = "path",
      value = "ID of the Payment that needs to be fetched")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Payment not found"),
    new ApiResponse(code = 400, message = "Invalid ID supplied")
  ))
  @Path("/{paymentId}")
  def getPayment(@ApiParam(hidden = true) guid: UUID): StandardRoute = {

    implicit val getPaymentMarshaller = ToResponseMarshaller.of[PaymentDetails](
      ContentTypes.`application/json`, ContentTypes.`text/plain(UTF-8)`, ContentTypes.`text/plain`
    ) {
      (result, contentType, context) =>
        val ctx = context.withContentTypeOverriding(contentType)
        ctx.marshalTo(HttpResponse(StatusCodes.OK, result.toJson.prettyPrint))
    }

    ucGetPayment.get(guid) match {
      case Left(error) => complete(StatusCodes.NotFound)
      case Right(detail) => complete(detail)
    }
  }
}

case class NewPaymentSwagger(
  @(ApiModelProperty @field)(required = true) invoiceId: String,
  @(ApiModelProperty @field)(required = true) value: String
)