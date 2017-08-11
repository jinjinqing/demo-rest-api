package rest

import java.util.UUID
import javax.ws.rs.Path

import services.invoice._
import io.swagger.annotations._
import spray.http._
import spray.httpx.marshalling.ToResponseMarshaller
import spray.routing._
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller
import spray.json._

import scala.annotation.meta.field

@Path("/invoice")
@Api(value = "/invoice")
class InvoiceRoute(
    ucCreateInvoice: UcCreateInvoice,
    ucGetInvoice: UcGetInvoice,
    ucListInvoice: UcListInvoice
) extends Directives with RestJsonFormatSupport {

  val invoicePath = "invoice"

  def tree: Route = {
    pathPrefix(invoicePath) {
      get {
        pathEndOrSingleSlash {
          list
        } ~
          pathPrefix(JavaUUID) { guid =>
            pathEnd {
              getInvoice(guid)
            }
          }
      } ~
        post {
          pathEnd {
            createInvoice()
          }
        }
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Returns a list of available Invoices")
  def list = {
    implicit val listInvoiceMarshaller = ToResponseMarshaller.of[List[Invoice]](
      ContentTypes.`application/json`, ContentTypes.`text/plain(UTF-8)`, ContentTypes.`text/plain`
    ) {
      (result, contentType, context) =>
        val ctx = context.withContentTypeOverriding(contentType)
        ctx.marshalTo(HttpResponse(StatusCodes.OK, result.toJson.prettyPrint))
    }
    complete(ucListInvoice.list)
  }

  @ApiOperation(value = "Creates a invoice", httpMethod = "POST", consumes = "application/json")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "Invoice object that needs to be created",
      dataType = "demo.rest.NewInvoiceSwagger", required = true, paramType = "body")
  ))
  def createInvoice() = {
    entity(as[NewInvoice]) { invoice =>
      ucCreateInvoice.create(invoice) match {
        case Left(error) => complete(StatusCodes.UnprocessableEntity)
        case Right(id) => complete(StatusCodes.Created)
      }
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Returns detailed information about invoice")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "invoiceId", required = true, dataType = "string", paramType = "path",
      value = "ID of the Invoice that needs to be fetched")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Invoice not found"),
    new ApiResponse(code = 400, message = "Invalid ID supplied")
  ))
  @Path("/{invoiceId}")
  def getInvoice(@ApiParam(hidden = true) guid: UUID): StandardRoute = {

    implicit val getInvoiceMarshaller = ToResponseMarshaller.of[InvoiceDetails](
      ContentTypes.`application/json`, ContentTypes.`text/plain(UTF-8)`, ContentTypes.`text/plain`
    ) {
      (result, contentType, context) =>
        val ctx = context.withContentTypeOverriding(contentType)
        ctx.marshalTo(HttpResponse(StatusCodes.OK, result.toJson.prettyPrint))
    }

    ucGetInvoice.get(guid) match {
      case Left(error) => complete(StatusCodes.NotFound)
      case Right(detail) => complete(detail)
    }
  }
}

case class NewInvoiceSwagger(
  @(ApiModelProperty @field)(required = true) customerId: String,
  @(ApiModelProperty @field)(required = true) invoiceDate: String,
  @(ApiModelProperty @field)(required = true) chargeName: String,
  @(ApiModelProperty @field)(required = true) toBePaid: String
)