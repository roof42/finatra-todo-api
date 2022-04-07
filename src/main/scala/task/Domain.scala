package task

trait Task {
  val id: Int
  val detail: String
}

case class Todo(id: Int, detail: String) extends Task
case class Doing(id: Int, detail: String) extends Task
case class Done(id: Int, detail: String) extends Task
