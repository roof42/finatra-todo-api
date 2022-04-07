package task
import scala.collection.mutable.SortedMap

class SortedMapTaskRepo {
  var todoList: SortedMap[Int, Todo] = SortedMap.empty[Int, Todo]
  var doingList: SortedMap[Int, Doing] = SortedMap.empty[Int, Doing]

  def getPong(): String = "pong"

  def createNewTodo(todo: Todo): Int = {
    var item = todoList.lastOption
    var id = item match {
      case Some(value) => value._1 + 1
      case None        => 0
    }
    todoList.addOne(id -> Todo(id, todo.detail))
    id
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
