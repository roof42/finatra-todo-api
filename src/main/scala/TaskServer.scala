import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import task._
object TaskServerMain extends TaskServer {}

class TaskServer extends HttpServer {
  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add[TaskResource]
  }
}
