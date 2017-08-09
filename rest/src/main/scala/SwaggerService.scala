package demo.rest

import com.github.swagger.spray.SwaggerHttpService
import com.github.swagger.spray.model.Info

trait SwaggerService extends SwaggerHttpService {

  val DefaultPort = 10076
  val DefaultInterface = "0.0.0.0"

  override val host: String = s"$DefaultInterface:$DefaultPort"

  override val basePath: String = "/demo"

  override val apiDocsPath = "api-docs"

  override val info = Info(
    version = "1.0.0",
    title = "DEMO-REST API",
    description = "API for communication with database"
  )

  override val apiTypes = SwaggerService.apiTypes
}

object SwaggerService {
  import scala.reflect.runtime.{universe => ru}

  val apiTypes = Seq(
    ru.typeOf[HealthCheckRoute],
    ru.typeOf[CustomerRoute],
    ru.typeOf[InvoiceRoute]
  )

}