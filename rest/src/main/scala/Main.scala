package demo.rest

import akka.actor._
import akka.io.IO
import akka.io.Tcp.{Bound, CommandFailed}
import akka.pattern._
import akka.routing._
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.StrictLogging
import spray.can._
import spray.can.server.ServerSettings
import scala.concurrent.duration._

object Main extends App with StrictLogging with Routes {

  lazy implicit val restExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  implicit val system = ActorSystem("demo-rest")

  val port = 10076
  val interface = "0.0.0.0"
  lazy val config = ConfigFactory.load()

  logger.info("DEMO-REST")

  try {
    val settings = ServerSettings.fromSubConfig(config.getConfig("spray.can.server")).copy(serverHeader = "demorest/1.0.0")

    val props = Props {
      new RouterActor(swaggerRoutes)
    }.withRouter(RoundRobinPool(Runtime.getRuntime().availableProcessors() * 4))

    val routerActorRef: ActorRef = system.actorOf(props)
    implicit val timeout: Timeout = 5.minutes
    val bindFuture = IO(Http) ? Http.Bind(routerActorRef, interface, port, settings = Some(settings))
    bindFuture.onSuccess {
      case Bound(address) => logger.info("Bound correctly to {}", address)
      case CommandFailed(b) => {
        logger.error("Unable to bind, will now exit")
        sys.exit(1)
      }
    }
  } catch {
    case t: Throwable =>
      logger.error("DEMO-REST startup failed: ", t)
      sys.exit(1)
  }

}
