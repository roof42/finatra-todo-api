package task

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.Request
import scala.collection.mutable.SortedMap
import com.google.inject.Inject

class TaskResource @Inject() (repo: SortedMapTaskRepo) extends Controller {
  get("/ping") { _: Request => repo.getPong }

  post("/todo/next") { todo: Todo =>
    var result = repo.moveToDoing(todo)
    result match {
      case Some(value) => response.created(s"Item was moved to doing")
      case None        => response.notFound(s"Item was not found")
    }
  }

  post("/todo") { plan: Plan =>
    val newTodo = repo.createNewTodo(plan)
    response.created(s"new todo was created")
  }

  get("/todo") { _: Request => repo.getAllItemInTodo() }
  get("/doing") { _: Request => repo.getAllItemInDoing() }
}
