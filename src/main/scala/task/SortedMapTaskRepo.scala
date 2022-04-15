package task
import scala.collection.mutable.SortedMap

class SortedMapTaskRepo {
  var todoList: SortedMap[Int, Todo] = SortedMap.empty[Int, Todo]
  var doingList: SortedMap[Int, Doing] = SortedMap.empty[Int, Doing]

  def getPong(): String = "pong"

  def createNewTodo(plan: Plan): Todo = {
    val id = generateId(plan)
    todoList.addOne(id -> Todo(id, plan.detail))
    todoList.last._2
  }

  private def generateId(plan: Plan): Int = {
    var item = todoList.lastOption
    item match {
      case Some(value) => value._1 + 1
      case None        => 0
    }
  }

  def moveToDoing(todo: Todo): Option[Doing] = {
    var item = todoList.get(todo.id)
    var result = item match {
      case Some(value) =>
        todoList.remove(value.id)
        var doing = Doing(value.id, value.detail)
        doingList.addOne(doing.id -> doing)
        Some(doingList.last._2)
      case None => None
    }
    result
  }

  def getAllItemInTodo(): List[Todo] = todoList.values.toList
  def getAllItemInDoing(): List[Doing] = doingList.values.toList
}
