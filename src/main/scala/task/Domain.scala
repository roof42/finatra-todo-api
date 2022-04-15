package task

trait Identifiable {
  val id: Int
}

abstract class Task {
  val detail: String
}
case class Plan(detail: String) extends Task

case class Todo(id: Int, detail: String) extends Task with Identifiable
case class Doing(id: Int, detail: String) extends Task with Identifiable
case class Done(id: Int, detail: String) extends Task with Identifiable
